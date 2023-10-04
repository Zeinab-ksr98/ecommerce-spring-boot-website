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

    @GetMapping("/get-orders")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<OnlineOrders> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping(value = "/get-order/{id}")
    public OnlineOrders getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping(value = "/create-order")
    public OnlineOrders createOrder(@RequestBody OnlineOrders onlineOrders) {
        return orderService.createOrder(onlineOrders);
    }

    @PutMapping(value = "/update-order/{id}")
    public OnlineOrders updateOrder(@PathVariable Long id, @RequestBody OnlineOrders onlineOrders) {
        onlineOrders.setId(id);
        return orderService.updateOrder(onlineOrders);
    }

    @DeleteMapping("/delete-order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }



//    methods with model
@GetMapping(value = "/checkout")
public String checkout (Model model) {
    return "order/checkout";
}
    @GetMapping(value = "/get-all-orders")
    public String displayOrders(Model model) {
        model.addAttribute("orders",getAllOrders());
        return "order/display-orders";
    }
    @GetMapping(value = "/delete-order/{ID}")
    public String deleteOrders(@PathVariable long ID){
        deleteOrder(ID);
        return "redirect:/get-all-orders";
    }

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
        createOrder(order);
        return "redirect:/get-all-orders";
    }
    @GetMapping(value = "/update-order/{ID}")
    public String UpdateOrders(@PathVariable long ID,Model model)  {
        model.addAttribute("order",getOrderById(ID) );
        model.addAttribute("products", productService.getAllProducts());
        return "order/Update-form";
    }
    @PostMapping(value = "/update-order")
    public String updateStudent(@Valid @ModelAttribute OnlineOrders order, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order/Update-form";
        }
        updateOrder(order.getId(),order);
        return "redirect:/get-all-orders";

    }


}
