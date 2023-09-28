package com.example.demo.dto;

import com.example.demo.model.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class CartDto {
    private long id;
//    private List<CartItemDto> cartItemList;
    private List<CartItem> cartItemList;
    private UUID userId;
}
