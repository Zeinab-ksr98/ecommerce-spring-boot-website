package com.example.demo.dto;

import com.example.demo.model.Product;
import com.example.demo.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OnlineOrdersDto {
    private Long id;
    private OrderStatus status;
    private double totalPrice;
//    private List<ProductDto> productList;
    private List<Product> productList;

    private UUID userId;
}
