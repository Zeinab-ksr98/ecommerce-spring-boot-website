package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryservice;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryservice) {
        this.productService = productService;
        this.categoryservice = categoryservice;
    }



    //No idea
    @GetMapping(value = "/Filter-all-products/{id}")
    public String categoriesProducts(@PathVariable long id,Model model) {
        model.addAttribute("products", productService.FilterAllAvailableProductsByCategory(id));
        return "display-products-c";
    }


    // No idea
    @GetMapping(value = "/manage-products-Filtered")
    public String manageProductsSorted(Model model) {
        model.addAttribute("products",productService.sortAllProducts());
        return "product/display-products";
    }


    // Adding a product
    @GetMapping(value="/add-product-form")
    public String showProductForm(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("categories",categoryservice.getAllCategories() );
        return "product/product-form";
    }
    @PostMapping(value = "/add-product-to-list")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/product-form";
        }
        productService.createProduct(product);
        return "redirect:/get-all-products";
    }


    //Updating Product APIs
    @GetMapping(value = "/update-product/{ID}")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("product",productService.getProductById(ID) );
        model.addAttribute("categories",categoryservice.getAllCategories() );
        return "product/Update-form";
    }
    @PostMapping(value = "/update-product")
    public String updateStudent(@Valid @ModelAttribute Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product/Update-form";
        }
        productService.updateProduct(product);
        return "redirect:/manage-products";

    }


    // Get all available products
    @GetMapping(value = "/get-all-products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllAvailableProducts());
        return "display-products-c";
    }

    //Get al Products
    @GetMapping(value = "/manage-products")
    public String manageProducts(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        return "product/display-products";
    }


    //Deleting Product API
    @GetMapping(value = "/delete-product/{ID}")
    public String deleteAProduct(@PathVariable long ID) {
        productService.deleteProduct(ID);
        return "redirect:/get-all-products";
    }


}
