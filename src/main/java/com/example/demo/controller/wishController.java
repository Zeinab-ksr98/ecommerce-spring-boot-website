package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.Wish;
import com.example.demo.security.UserInfoDetails;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class wishController {
    private final WishService wishService;
    private final ProductService productService;
    private final UserService userService;


    public wishController(WishService wishService, ProductService productService, UserService userService) {
        this.wishService = wishService;
        this.productService = productService;
        this.userService = userService;
    }


    @GetMapping(value = "/display-wishList")
    @PreAuthorize("hasAuthority('USER')")
    public String displayOrders(Model model) {
            User customUser = userService.getCurrentUser();
            model.addAttribute("wish",wishService.getWishById(customUser.getWish().getId()));
            return "wish/wishlist";
    }

    @GetMapping(value="/add-wish/{pID}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String modifyWish(@PathVariable long pID) {

            UUID userid=userService.getCurrentUser().getId();
            User user=userService.getUserById(userid);
            Product p=productService.getProductById(pID);
            if(!user.getWish().getProductList().contains(p)){
                    user.getWish().getProductList().add(p);
                    user.getWish().setProductList(user.getWish().getProductList());
                    userService.save(user);
            }
            return "redirect:/home" ;
    }

    @GetMapping(value = "/delete-wish/{pID}")
    public String delete_wishedProduct(@PathVariable long pID) {
        UUID userid=userService.getCurrentUser().getId();
        User user=userService.getUserById(userid);
        Product p=productService.getProductById(pID);
        user.getWish().getProductList().remove(p);
        user.getWish().setProductList(user.getWish().getProductList());
        userService.save(user);
        return "redirect:/display-wishList";
    }



}
