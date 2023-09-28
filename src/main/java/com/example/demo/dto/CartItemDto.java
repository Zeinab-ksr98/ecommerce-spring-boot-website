package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private ProductDto product;
    private int quantity;
}
