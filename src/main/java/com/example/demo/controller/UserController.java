package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/users")

public class UserController {

    private final  UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    @Autowired
    public UserController(UserService userService, ProductService productService, CategoryService categoryService){
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/create-initial-admin")
    public String createInitialAdmin(){
        System.out.println("hi");
        User user = new User("zeinabksr", "zk@gmail.com", "123", "03010131", true);
        try{
            User admin = userService.createUser(user,true);
            System.out.println("done");
            return "Admin Successfully Created";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @GetMapping(value = "/create-initial-user")
    public String createInitialUser(){
        User user = new User("czk", "czk@gmail.com", "123", "03010135", false);
        try{
            User admin = userService.createUser(user,false);
            return "User Successfully Created";
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
    @GetMapping(value = "/block/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String Block(@PathVariable("id") String id){
        userService.save(userService.BlockUser(UUID.fromString(id)));
        userService.save(userService.deActivateUser(UUID.fromString(id)));
        return "redirect:/manage-users";

    }
    @GetMapping(value = "/deactivate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deActivateUser(@PathVariable("id") String id){
        userService.save(userService.deActivateUser(UUID.fromString(id)));
        return "redirect:/manage-users";

    }

    @GetMapping(value = "/activate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String activateUser(@PathVariable("id") String id){
        userService.save(userService.activate(UUID.fromString(id)));
        return "redirect:/manage-users";
    }
}
