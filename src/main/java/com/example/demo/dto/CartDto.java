package com.example.demo.dto;

import com.example.demo.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private long id;
    private List<CartItem> cartItemList;
}
