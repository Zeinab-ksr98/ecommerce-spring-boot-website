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

    @GetMapping(value="/modify-wish/{pID}")
    public String modifyWish(@PathVariable long pID,Model model,Authentication authentication) {

            Wish w=userService.getCurrentUser().getWish();
             Product p= productService.getProductById(pID);
             List<Product>pl=w.getProductList();
             pl.add(p);
            w.setProductList(pl);
            wishService.updateWish(w);
          return null ;
    }

    @GetMapping(value = "/remove-product/{pID}")
    public String remove_wishedProduct(@PathVariable long pID) {

        return "wish/wishlist";
    }


}
