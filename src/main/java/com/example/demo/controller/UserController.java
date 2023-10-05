package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping("/users")

public class UserController {

    private final  UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/create-initial-admin")
    public String createInitialAdmin(){
        User user = new User("mhmd", "mhmd@cx.com", "123", "81344864", true);
        try{
            User admin = userService.createUser(user,true);
            System.out.println("done");
            return "Admin Successfully Created";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping(value = "/create")
    public String createUser(@ModelAttribute("newuser") User user){
        if(userService.userNameExists(user.getUsername()))
            return "account/SignIn";
        if(userService.userEmailExists(user.getEmail()))
            return "account/SignIn";
        try {
            userService.createUser(user,false);
            return "redirect:/home";
        } catch (Exception e) {
            return "account/SignIn";
        }
    }

    @GetMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getUsers(){
        try{
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.getUserById(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/deactivate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deActivateUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.deActivateUser(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/activate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activateUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.activate(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
