package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    private UserService userService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/manage-category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageCategory(Model model) {
        model.addAttribute("userrole",userService.getCurrentUser().getRoles().toString());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "categories/manage-category";
    }

    @PostMapping("/add-category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addCategory(@RequestParam("categoryname") String categoryName,@RequestParam("img") String img) {
        Category category = new Category();
        category.setName(categoryName);
        category.setImg(img);
        categoryService.saveCategory(category);
        return "redirect:/manage-category";
    }

    @PostMapping("/update-category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String updateCategory(@RequestParam("id") Long id, @RequestParam("updatedName") String name,@RequestParam("updatedImage") String img){
        System.out.println(id + " "+name +" "+img);
        Category category = categoryService.getCategoryById(id);
        category.setName(name);
        category.setImg(img);
        categoryService.saveCategory(category);
        return "redirect:/manage-category";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/manage-category";
    }


}