package ru.zatert.springbootsecuritycrud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.zatert.springbootsecuritycrud.entities.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("/users")
//public class MainController {
//    private List<User> USERS = Stream.of(
//            new User(1L, "Ivan", "ivan", "ivan@mail.ru"),
//            new User(2L, "Petr", "Petrov")
//    ).collect(Collectors.toList());
//
//    @GetMapping
//    public List<User> getAll(){
//        return USERS;
//    }
//    @GetMapping("/{id}")
//    public User getById(@PathVariable Long id){
//        return USERS.stream().filter(user -> user.getId().equals(id))
//    .findFirst().orElse(null);
//    }
//
//@PostMapping
//public User create(@RequestBody User user){
//        this.USERS.add(user);
//        return user;
//    }
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id){
//        this.USERS.removeIf(user ->user.getId().equals(id));
//    }
//
////    @GetMapping("/")
////    public String homePage(){
////        return "home";
////    }
//    @GetMapping("/hello")
//    public String pageForAuthentictedUsers(Principal principal){
//       return "hello";
//        // return "secured part of web service" + principal.getName();
//    }
//@RequestMapping(value = "hello", method = RequestMethod.GET)
//public String printWelcome(ModelMap model) {
//    List<String> messages = new ArrayList<>();
//    messages.add("Hello!");
//    messages.add("I'm Spring MVC-SECURITY application");
//    messages.add("5.2.0 version by sep'19 ");
//    model.addAttribute("messages", messages);
//    return "hello";
//}
//
//    @RequestMapping(value = "hello", method = RequestMethod.GET)
//    public String loginPage() {
//        return "hello";
//    }
//}
