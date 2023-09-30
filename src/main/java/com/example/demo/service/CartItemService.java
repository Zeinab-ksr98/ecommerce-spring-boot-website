package com.example.demo.service;


import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartItemService( CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }


    public double getSubPrice(Long id){
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        Product product = productService.getProductById(cartItem.getProduct().getId());
        return cartItem.getQuantity()*product.getPrice()*(1.0-product.getDiscount());
    }

}
