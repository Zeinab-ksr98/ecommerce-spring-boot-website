package com.example.demo.controller;

import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class home {
    private final ProductService productService;
    private final CategoryService categoryService;

    public home(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/home")
    public String displayHome(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Home";
    }
    @GetMapping(value = "/home1")
    public String Home(Model model) {
        return "Home1";
    }
    @GetMapping(value = "/cm")
    public String displayH(Model model) {
        return "cmain";
    }

    @GetMapping(value = "/display")
    public String UpdateOrders( Model model)  {
        model.addAttribute("products",productService.getAllProducts());
        return "display-products-c";
    }
    @GetMapping(value = "/display-a-product/{ID}")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("product", productService.getProductById(ID) );
        return "display-a-product";
    }

}
