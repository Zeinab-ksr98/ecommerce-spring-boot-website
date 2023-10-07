package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class accountController {
    @GetMapping(value = "/SignIn")
    public String SignIn(Model model) {
        model.addAttribute("newuser",new User());
        return "account/login_signup";
    }
    @GetMapping(value = "/create-admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String CreateAdmin(Model model) {
        model.addAttribute("newuser",new User());
        return "account/createAdmin";
    }

}
