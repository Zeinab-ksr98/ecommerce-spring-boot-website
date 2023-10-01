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

    @GetMapping("/get-products")
    public List<Product> getAllProducts() {
        return productService.getAllAvailableProducts();
    }
    @GetMapping("/Filter-products")
    public List<Product> FilterAllProducts(Long id) {
        return productService.FilterAllAvailableProductsByCategory(id);
    }
    public List<Product> manageAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/manage-products-filter")
    public List<Product> manageAllProductsFiltered() {
        return productService.sortAllProducts();
    }

    @GetMapping(value = "/get-product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(value = "/create-product")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping(value = "/update-product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


//with the model
    @GetMapping(value = "/get-all-products")
    public String getProducts(Model model) {
        model.addAttribute("products", getAllProducts());
        return "display-products-c";
    }
    @GetMapping(value = "/Filter-all-products/{id}")
    public String categoriesProducts(@PathVariable long id,Model model) {
        model.addAttribute("products", FilterAllProducts(id));
        return "display-products-c";
    }
    @GetMapping(value = "/manage-products")
    public String manageProducts(Model model) {
        model.addAttribute("products",manageAllProducts());
        return "product/display-products";}
    @GetMapping(value = "/manage-products-Filtered")
    public String manageProductsSorted(Model model) {
        model.addAttribute("products",manageAllProductsFiltered());
        return "product/display-products";}
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
        createProduct(product);
        return "redirect:/get-all-products";
    }

    @GetMapping(value = "/delete-product/{ID}")
    public String deleteAProduct(@PathVariable long ID) {
        deleteProduct(ID);
        return "redirect:/get-all-products";
    }
    @GetMapping(value = "/update-product/{ID}")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("product",getProductById(ID) );
        model.addAttribute("categories",categoryservice.getAllCategories() );
        return "product/Update-form";
    }
    @PostMapping(value = "/update-product")
    public String updateStudent(@Valid @ModelAttribute Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product/Update-form";
        }
        updateProduct(product.getId(),product);
        return "redirect:/manage-products";

    }


}
