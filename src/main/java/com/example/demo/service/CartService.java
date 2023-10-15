package com.example.demo.service;



import com.example.demo.dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    @Autowired
    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id).orElse(null);
    }

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

    public void setTotal(Cart cart) {
        List<CartItem> cartItemList = cart.getCartItemList();
        double total = 0.0;
        if (cartItemList != null) {
            for (CartItem cartItem : cartItemList) {
                double subTotal = cartItemService.getSubPrice(cartItem.getId());
                total += subTotal;
            }
        }
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }

    public CartDto mapToDto(Cart cart){
        return new CartDto(cart.getId(),cart.getCartItemList());
    }
    public List<CartDto> mapToDtoList(List<Cart> cart){
        return cart.stream().map(this::mapToDto).collect(Collectors.toList());
    }


}
