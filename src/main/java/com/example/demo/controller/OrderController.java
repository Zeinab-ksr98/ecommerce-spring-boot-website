package com.example.demo.controller;


import com.example.demo.model.OnlineOrders;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;


    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }



    //Create new Order
    @GetMapping(value="/add-order-form")
    public String showOrderForm(Model model) {
        model.addAttribute("order",new OnlineOrders());
        model.addAttribute("products",productService.getAllProducts());
        return "order/Order-form";
    }
    @PostMapping(value = "/add-order-to-list")
    public String addStudent(@Valid @ModelAttribute OnlineOrders order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Order-form";
        }
        orderService.createOrder(order);
        return "redirect:/get-all-orders";
    }


    //Update Orders APIs
    @GetMapping(value = "/update-order/{ID}")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("order",orderService.getOrderById(ID));
        model.addAttribute("products", productService.getAllProducts());
        return "order/Update-form";
    }
    @PostMapping(value = "/update-order")
    public String updateStudent(@Valid @ModelAttribute OnlineOrders order, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order/Update-form";
        }
        orderService.updateOrder(order);
        return "redirect:/get-all-orders";

    }

    // Get all orders
    @GetMapping(value = "/get-all-orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String displayOrders(Model model) {
        model.addAttribute("orders",orderService.getAllOrders());
        return "order/display-orders";
    }


    // Checkout
    @GetMapping(value = "/checkout")
    public String checkout (Model model) {
        return "order/checkout";
    }

    //Deleting Order
    @GetMapping(value = "/delete-order/{ID}")
    public String deleteOrders(@PathVariable long ID){
        orderService.deleteOrder(ID);
        return "redirect:/get-all-orders";
    }

}
