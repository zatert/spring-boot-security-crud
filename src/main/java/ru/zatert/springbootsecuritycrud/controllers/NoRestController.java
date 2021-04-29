package ru.zatert.springbootsecuritycrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zatert.springbootsecuritycrud.entities.User;
import ru.zatert.springbootsecuritycrud.repositories.UserRepo;
import ru.zatert.springbootsecuritycrud.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/auth")
public class NoRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/hello")
    public String getSuccessPage() {
        return "hello";
    }

    //@PostMapping( "/user")
    @RequestMapping(value = "/admin")
    public String pageAllUsers(Principal principal, Model model) {
        List<User> list = userService.findAll();
        //User user = userService.findByUsername(principal.getName());
        model.addAttribute("list", list);
        //model.addAttribute("user", user);
        return "admin";
        //return "secured part of web service" + user.getUsername() + " " + user.getEmail();
    }
    //@RequestMapping(value = "/delete", method = RequestMethod.POST)
    @GetMapping("/delete")
    private String deleteUser(@RequestParam Long id, Model model){
        userService.deleteById(id);
        System.out.println("User_Id : ");
        model.addAttribute("list", userService.findAll());
        return "redirect:/admin";
    }
    @RequestMapping(value = "/user")
    public String pageForSingleUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
    //@RequestMapping(value = "/admin", method = RequestMethod.POST)
    @PostMapping("/add")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String email,
                      Model model){
        User user = new User(username, password, email);
        userService.save(user);
//        List<User> list = userService.findAll();
//        model.addAttribute("list", list);
        return "redirect:/admin";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(long id) {
        return userService.findById(id);
    }

    @PostMapping("/save")
    public String save(User user) {
        userService.save(user);
        return "redirect:/admin";
    }
}
