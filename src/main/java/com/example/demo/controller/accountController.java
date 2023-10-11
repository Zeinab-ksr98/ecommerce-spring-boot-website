package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class accountController {
    private final ProductService productService;
    private final UserService userService;

    private final CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;

    public accountController (ProductService productService, UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @GetMapping(value = "/SignIn")
    public String SignIn(Model model) {
        model.addAttribute("newuser",new User());
        return "account/login_signup";
    }
    @GetMapping(value = "/create-admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String CreateAdmin(Model model) {
        model.addAttribute("newuser",new User());
        return "account/createAdmin";
    }
    @GetMapping(value = "/home")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String displayHome(Model model) {
        model.addAttribute("products",productService.getAllAvailableProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "account/Home";
    }
    //profile

    @GetMapping("/profile")
    public String userProfile(Model model) {
        User user =userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("userrole",userService.getCurrentUser().getRoles());
        return "account/Profile";
    }

    @PostMapping("/profile-edit")
    public String editUserProfile(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/profile";
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/profile-resetPassword")
    public String resetPassword(@RequestParam String newPassword) {
        User user = userService.getCurrentUser();
        user.setPassword( passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "redirect:/profile";
    }
    @GetMapping("/forgetPage")
    public String forgetPage() {
        return "account/forgetPass";
    }
    @PostMapping("/forget_pass")
    public String forgetPassword(@RequestParam String resetpassword,@RequestParam String reusername,@RequestParam String reemail, RedirectAttributes redirectAttributes) {
        User user = userService.findUserByEmailAndUserName(reusername,reemail).orElse(null);
        if(user!=null){
            user.setPassword( passwordEncoder.encode(resetpassword));
            userRepository.save(user);
            return "redirect:/SignIn";
        }
        else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/forgetPage";
        }

    }
//    header
    @GetMapping("/header")
    public String Header(Model model) {
        model.addAttribute("userrole",userService.getCurrentUser().getRoles());
        return "fragments/header";
    }
}





