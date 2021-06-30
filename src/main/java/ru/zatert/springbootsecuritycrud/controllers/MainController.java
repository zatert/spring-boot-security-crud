package ru.zatert.springbootsecuritycrud.controllers;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.zatert.springbootsecuritycrud.entities.User;
import ru.zatert.springbootsecuritycrud.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
//@RequestMapping("/auth")
public class MainController {
    @Autowired
    private UserService userService;

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }

//    @RequestMapping(value="/auth", method = RequestMethod.GET)//{id}
//    public List<User> getone(){//@RequestParam(required = true) Long id) {  ResponseEntity<User>
//        //User user = userService.findById(1L);
//        List<User> list = new ArrayList<>();
//        userService.findAll().forEach(list::add);
//        return list;
//       //return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//    @PostMapping("/add_user")
//    public void addUser(@RequestBody User user) {
//        userService.save(user);
//    }

/*---------========================== GET ONE =========================------------------*/
    @RequestMapping(value="/get_one/{id}", method = RequestMethod.PUT)//
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(id);  //  добавим this
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*---------========================== ADD =========================------------------*/
    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user){
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.save(user);
        return new  ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /*---------========================== EDIT =========================------------------*/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT) //{id}
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        User currentUser = userService.findById(id);
        System.out.println(user);
       //System.out.println(username);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        currentUser.setId(id);
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(user.getPassword());
        currentUser.setEmail(user.getEmail());
        this.userService.save(currentUser);
        //System.out.println(user + "   " + currentUser);
        return new  ResponseEntity<>(currentUser, HttpStatus.OK);
    }


    /*---------========================== DELETE =========================------------------*/
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id){
        User user = this.userService.findById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*---------========================== GET ALL =========================------------------*/
    @RequestMapping(value = "/all_users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll(){
//        List<User> list = this.userService.findAll();
        List<User> list = new ArrayList<>();
        userService.findAll().forEach(list::add);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
