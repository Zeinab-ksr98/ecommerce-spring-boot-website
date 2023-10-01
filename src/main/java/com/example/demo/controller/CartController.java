package com.example.demo.controller;


import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    private  CartController(CartService cartService, UserService userService, ProductService productService){
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping(value = "/update-cart")
    public Cart updateCart(@RequestParam String product, @RequestParam String userId){
        System.out.printf(userId);
        User user = userService.getUserById(UUID.fromString(userId));
        Cart cart = cartService.getCartById(user.getCart().getId());
        cart.setUser(user);
        System.out.printf(product);
        List<Product> products = new ArrayList<>();
        products.add(productService.getProductById(Long.parseLong(product)));
//
//        cart.setProductList(products);
        cartService.updateCart(cart);
        return null;
    }
}
