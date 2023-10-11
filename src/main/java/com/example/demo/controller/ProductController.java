package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.security.UserInfoDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    // Get all available products user side
    @GetMapping(value = "/get-all-products")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllAvailableProducts());
        return "product/display-products-c";
    }
    @GetMapping(value = "/display-a-product/{ID}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String displayProductDetails(@PathVariable long ID, Model model)  {
        model.addAttribute("product", productService.getProductById(ID) );
        return "product/display-a-product";
    }

    //Get all Products admin side
    @GetMapping(value = "/manage-products")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String manageProducts(Model model,Authentication authentication) {
        model.addAttribute("products",productService.getAllProducts());
        return "product/manage-products";
    }
    //display products acc to category for the user
    @GetMapping(value = "/cat-all-products")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String displayAllProducts(Model model) {
        model.addAttribute("products", productService.getAllAvailableProducts());
        return "product/display-products-c";
    }
//    @GetMapping(value = "/Filter-all-products/{categoryId}")
//    @PreAuthorize("hasAnyAuthority('USER')")
//    public String categoriesProducts(@PathVariable long categoryId, Model model) {
//        List<Product> filteredProducts = productService.FilterAllAvailableProductsByCategory(categoryId);
//        model.addAttribute("products", filteredProducts);
//        return "product/display-products-c";
//    }
    @RequestMapping("/Filter-all-products/{categoryId}")
    @ResponseBody
    public List<Product> filterAllProductsByCategory(@PathVariable Long categoryId) {
        if ("all".equals(categoryId)) {
            return productService.getAllAvailableProducts();
        } else {
            return productService.FilterAllAvailableProductsByCategory(categoryId);
        }
    }
    // sort displayed products for admin
    @GetMapping(value = "/manage-products-Filtered")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String manageProductsSorted(Model model) {
        model.addAttribute("products",productService.sortAllProducts());
        return "manage-products";
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


}
