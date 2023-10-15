package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.model.enums.OrderStatus;
import com.example.demo.security.UserInfoDetails;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping(value = "/get-all-orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String displayOrders(Model model) {
        model.addAttribute("userrole",userService.getCurrentUser().getRoles().toString());
        model.addAttribute("orders",orderService.getAllOrders());
        return "order/display-orders";
    }


    @PostMapping("/update-status")
    public String updateStatus(@RequestParam("id") Long id, @RequestParam("updatedStatus") OrderStatus status){
        System.out.println(id + " "+status);
        OnlineOrders order = orderService.getOrderById(id);
        order.setStatus(status);
        orderService.updateOrder(order);
        return "redirect:/get-all-orders";
    }

    @GetMapping(value="/cartToOrder")
    public String CartToOrder() {
        User user=userService.getUserById(userService.getCurrentUser().id);
        if (areUserDetailsComplete(user)) {
        Cart c=user.getCart();
        OnlineOrders order= new OnlineOrders();
        order.setUser(user);
        order.setStatus(OrderStatus.IN_PROCESS);
        order.setTotalPrice(c.getTotalPrice());
        order.setOrdersList(new ArrayList<CartItem>(c.getCartItemList()));
        orderService.createOrder(order);
        c.setTotalPrice(0.0);
        c.setCartItemList(new ArrayList<>());
        cartService.save(c);
        return "redirect:/display-cart";
        }
        else{
            return"redirect:/display-cart#errorModal" ;
        }

    }
    private boolean areUserDetailsComplete(User user) {
        return !user.getAddresses().isEmpty() && user.getPhone() != null && user.getUsername() != null;
    }
}
