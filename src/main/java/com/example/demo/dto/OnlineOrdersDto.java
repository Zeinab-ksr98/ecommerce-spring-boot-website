package com.example.demo.dto;

import com.example.demo.model.Product;
import com.example.demo.model.enums.OrderStatus;
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
public class OnlineOrdersDto {
    private Long id;
    private OrderStatus status;
    private double totalPrice;
    private List<Product> productList;

}
