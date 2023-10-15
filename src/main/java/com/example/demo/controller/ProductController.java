package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.security.UserInfoDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller

public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryservice;

    @Autowired
    private UserService userService;

    // display product's details
    @GetMapping(value = "/display-a-product/{ID}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String displayProductDetails(@PathVariable long ID, Model model)  {
        model.addAttribute("product", productService.getProductById(ID) );
        return "product/display-a-product";
    }
    //Get all Products admin side
    @GetMapping(value = "/manage-products")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String manageProducts(Model model) {
        model.addAttribute("userrole",userService.getCurrentUser().getRoles().toString());
        UUID sellerId=userService.getCurrentUser().getId();
        model.addAttribute("products",productService.getAllProductsForSeller(sellerId));
        return "product/manage-products";
    }

    // sort displayed products for admin
    @GetMapping(value = "/manage-products-Filtered")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String manageProductsSorted(Model model) {
        model.addAttribute("userrole",userService.getCurrentUser().getRoles().toString());
        UUID sellerId=userService.getCurrentUser().getId();
        model.addAttribute("products",productService.SortAllProductsForSeller(sellerId));
        return "product/manage-products";
    }
    // Adding a product
    @GetMapping(value="/add-product-form")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String showProductForm(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("categories",categoryservice.getAllCategories() );
        return "product/product-form";
    }
    @PostMapping(value = "/add-product-to-list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addProduct(@Valid @ModelAttribute Product product, Authentication authentication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/product-form";
        }

        // Check if the principal is an instance of UserInfoDetails
        if (authentication.getPrincipal() instanceof UserInfoDetails) {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            User customUser = userInfoDetails.getUser();
            product.setSeller(customUser);
            productService.createProduct(product);

            return "redirect:/manage-products";
        } else {
            return "redirect:/error";
        }
    }

    //Updating Product APIs
    @GetMapping(value = "/update-product/{ID}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("product",productService.getProductById(ID) );
        model.addAttribute("categories",categoryservice.getAllCategories() );
        return "product/Update-form";
    }
    @PostMapping(value = "/update-product")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String updateStudent(@Valid @ModelAttribute Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product/Update-form";
        }
        productService.updateProduct(product);
        return "redirect:/manage-products";

    }



    //Deleting Product API
    @GetMapping(value = "/delete-product/{ID}")
    public String deleteAProduct(@PathVariable long ID) {
        productService.deleteProduct(ID);
        return "redirect:/manage-products";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null) {
            List<Product> products = productService.searchProducts(keyword);
            model.addAttribute("products", products);
        }
        return "product/search-result";
    }

}
