package com.example.demo.controller;


import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@PreAuthorize("hasRole('ADMIN')")
@Controller
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public  CartController(CartService cartService, CartItemService cartItemService, UserService userService, ProductService productService){
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping(value = "/display-cart")
    @PreAuthorize("hasAuthority('USER')")
    public String displayCart(Model model) {
        User customUser = userService.getUserById(userService.getCurrentUser().id);
        model.addAttribute("cartItems",cartService.getCartById(customUser.getCart().getId()).getCartItemList());
        model.addAttribute("cart",cartService.getCartById(customUser.getCart().getId()));

        return "order/cart";
    }

@GetMapping(value = "/add-cart/{pID}")
@PreAuthorize("hasAnyAuthority('USER')")
public String addItemToCart(@PathVariable long pID) {
    UUID userId = userService.getCurrentUser().getId();
    User user = userService.getUserById(userId);

    Cart cart = user.getCart();
    Product product = productService.getProductById(pID);
    // Check if the cart already contains the product
    boolean productExistsInCart = cart.getCartItemList().stream()
            .anyMatch(cartItem -> cartItem.getProduct().getId() == pID);

    if (!productExistsInCart) {
//         If the product is not in the cart, create a new CartItem and add it to the cart
        CartItem cartItem = new CartItem(product, 1);
        cart.getCartItemList().add(cartItem);
        cart.setUser(user);
        cartItemService.save(cartItem);
        cartService.setTotal(cart);
        cartService.save(cart);

    }
    return "redirect:/home";
}

    @GetMapping(value = "/delete-cart/{pID}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String deleteFromCart(@PathVariable long pID) {
        UUID userId = userService.getCurrentUser().getId();
        User user = userService.getUserById(userId);

        Cart cart = user.getCart();
        CartItem cartItem = cartItemService.getCartItemByProductId(cart.getCartItemList(),pID);
        cart.getCartItemList().remove(cartItem);
        cartItemService.save(cartItem);
        cartService.setTotal(cart);
        cartService.save(cart);

        return "redirect:/display-cart";
    }
    @GetMapping(value = "/modify-quantity-cart/{pID}/{q}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String modifyItemInCart(@PathVariable long pID,@PathVariable int q) {
        UUID userId = userService.getCurrentUser().getId();
        User user = userService.getUserById(userId);

        Cart cart = user.getCart();
        CartItem cartItem = cartItemService.getCartItemByProductId(cart.getCartItemList(),pID);
        cartItem.setQuantity(q);
        cartItemService.save(cartItem);
        cartService.setTotal(cart);
        cartService.save(cart);

        return "redirect:/display-cart";
    }
//    @GetMapping(value = "/modify-quantity-cart/{pID}/{q}")
//    @PreAuthorize("hasAnyAuthority('USER')")
//    public String modifyItemInCart(@PathVariable long pID, @PathVariable int q) {
//        UUID userId = userService.getCurrentUser().getId();
//        User user = userService.getUserById(userId);
//
//        Cart cart = user.getCart();
//        Product product = productService.getProductById(pID);
//
//        // Find the existing CartItem for the specified product in the cart
//        CartItem existingCartItem = null;
//        for (CartItem cartItem : cart.getCartItemList()) {
//            if (cartItem.getProduct().getId() == pID) {
//                existingCartItem = cartItem;
//                break;
//            }
//        }
//
//        if (existingCartItem != null) {
//            // Update the quantity of the existing CartItem
//            existingCartItem.setQuantity(q);
//        } else {
//            // If the product is not in the cart, create a new CartItem
//            CartItem cartItem = new CartItem(product, q);
//            cart.getCartItemList().add(cartItem);
//        }
//
//        cart.setUser(user);
//        userService.save(user);
//
//        return "redirect:/display-cart";
//    }





}
