package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    @Autowired
    public AdminController(UserService userService, CategoryService categoryService,ProductService  productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }


}