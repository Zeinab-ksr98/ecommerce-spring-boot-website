package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
public class accountController {
    private final ProductService productService;
    private final CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;

    public accountController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/Main")
    public String home(Model model) {
        return "index";
    }


    @GetMapping(value = "/SignIn")
    public String SignIn(Model model) {
        model.addAttribute("newuser", new User());
        return "account/login_signup";
    }

    @GetMapping(value = "/home")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String displayHome(Model model, @RequestParam(name = "catid", required = false) Long catid) {
        model.addAttribute("userrole", userService.getCurrentUser().getRoles().toString());

        if (catid == null) {
            model.addAttribute("products", productService.getAllProducts());
        } else {
            model.addAttribute("products", productService.FilterAllProductsByCategory(catid));
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        return "account/Home";
    }
    //profile

    @GetMapping("/profile")
    public String userProfile(Model model) {
        //current user is from cach
        User user = userService.getCurrentUser();
        model.addAttribute("orders", orderService.getAllOrdersForCustomer(user.id));
        model.addAttribute("user", userService.getUserById(user.id));
        model.addAttribute("userrole", userService.getCurrentUser().getRoles().toString());
        model.addAttribute("address", new Address());

        System.out.println(userService.getCurrentUser().getRoles());
        return "account/Profile";
    }

    @PostMapping("/profile-edit")
    public String editUserProfile(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/address-edit")
    public String editUseraddress(@ModelAttribute("address") Address address) {
        User user = userService.getUserById(userService.getCurrentUser().id);
        List<Address> addresses = user.getAddresses();
//        enable user to add only 2 addresses
        if (addresses.size() < 2) {
            addresses.add(address);
            user.setAddresses(addresses);
            addressService.save(address);
            userService.save(user);
            return "redirect:/profile";
        }
        return "redirect:/profile#errorModal";
    }

    @GetMapping("/delete-address/{addressID}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String deleteAddress(@PathVariable Long addressID) {
        Address address = addressService.getAddressById(addressID);
        if (address != null) {
            User user = userService.getUserById(userService.getCurrentUser().id);
            List<Address> addresses = user.getAddresses();
            addresses.remove(address);
            user.setAddresses(addresses);
            userService.save(user);
            addressService.deleteAddress(address);
        }

        return "redirect:/profile#";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/profile-resetPassword")
    public String resetPassword(@RequestParam String newPassword) {
        User user = userService.getCurrentUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/forgetPage")
    public String forgetPage() {
        return "account/forgetPass";
    }

    @PostMapping("/forget_pass")
    public String forgetPassword(@RequestParam String resetpassword, @RequestParam String reusername, @RequestParam String reemail, RedirectAttributes redirectAttributes) {
        User user = userService.findUserByEmailAndUserName(reusername, reemail).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(resetpassword));
            userService.save(user);
            return "redirect:/SignIn";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/forgetPage";
        }

    }
//    manage users as admin

    @GetMapping("/manage-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageCategory(Model model) {
        model.addAttribute("userrole", userService.getCurrentUser().getRoles().toString());
        model.addAttribute("users", userService.getAllUsersNotBlocked());
        return "account/manage-users";
    }

    @PostMapping(value = "/create-admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String CreateAdmin(@RequestParam("username") String userName, @RequestParam("pass") String pass, @RequestParam("email") String email, @RequestParam("phone") String phone) {
        User user = new User(userName, email, pass, phone, true);
        userService.createUser(user, true);
        return "redirect:/manage-users";
    }
}





