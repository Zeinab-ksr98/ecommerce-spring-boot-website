package com.example.demo.service;


import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartItemService( CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }
    public List<CartItem> getAllCartItems(){
        return cartItemRepository.findAll();
    }
    public CartItem save(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
    public CartItem getCartItemByProductId( List<CartItem> cartItems,long productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == productId) {
                return cartItem;
            }
        }
        return null;
    }

    public double getSubPrice(Long id){
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        double cost =cartItem.getQuantity()*cartItem.getProduct().getPrice();
        double dc= cost*((cartItem.getQuantity()*cartItem.getProduct().getDiscount())/100);
        return cost-dc;
    }

}
