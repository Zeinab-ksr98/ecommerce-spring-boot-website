package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/admin")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/manage-category")
    public String manageCategory(Model model) {
        model.addAttribute("categories",categoryService.getAllCategories());
        return "categories/manage-category";
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestParam("categoryname") String categoryName,@RequestParam("img") String img) {
        // Create a new category and save it
        Category category = new Category();
        category.setName(categoryName);
        category.setImg(img);
        categoryService.saveCategory(category);
        return "redirect:/manage-category";
    }
    @PostMapping("/update-category")
    public String updateCategory(@RequestParam("id") Long id, @RequestParam("updatedName") String name,@RequestParam("updatedImage") String img){
        System.out.println(id + " "+name +" "+img);
        Category category = categoryService.getCategoryById(id);
        category.setName(name);
        category.setImg(img);
        categoryService.saveCategory(category);
        return "redirect:/manage-category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/manage-category";
    }


}