//package com.example.demo.controller;
//
//import com.example.demo.model.Cart;
//import com.example.demo.model.Product;
//import com.example.demo.model.User;
//import com.example.demo.model.enums.Role;
//import com.example.demo.service.CartService;
//import com.example.demo.service.ProductService;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class testController {
//
//    private final UserService userService;
//    private final CartService cartService;
//    private final ProductService productService;
//
//    @Autowired
//    private testController(UserService userService, CartService cartService, ProductService productService){
//        this.userService = userService;
//        this.cartService = cartService;
//        this.productService = productService;
//    }
//    @PostMapping(value = "/test")
//    public User test(@RequestParam String email, @RequestParam String password){
//        System.out.printf(email);
//        List<Role> roles = new ArrayList<>();
//        roles.add(Role.USER);
//        Cart cart = new Cart();
//        cartService.createWish(cart);
//        return userService.createUser(new User(email,password,roles,cartService.createWish(cart)));
//    }
//
//
//    @PostMapping(value = "create-new-product")
//    public Product createProduct(@RequestBody Product product){
//        return productService.createProduct(product);
//    }
//}
