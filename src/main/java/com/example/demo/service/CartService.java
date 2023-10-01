package com.example.demo.service;



import com.example.demo.dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id).orElse(null);
    }

    public Cart createWish(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart){
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id){
        cartRepository.deleteById(id );
    }

    public double getTotal(Cart cart) {
        List<CartItem> cartItemList = cart.getCartItemList();

        double total = 0.0;
        if (cartItemList != null) {
            for (CartItem cartItem : cartItemList) {
                double subTotal = cartItemService.getSubPrice(cartItem.getId());
                total += subTotal;
            }
        }
        return total;
    }
    public CartDto mapToDto(Cart cart){
        return new CartDto(cart.getId(),cart.getCartItemList());
    }
    public List<CartDto> mapToDtoList(List<Cart> cart){
        return cart.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}
