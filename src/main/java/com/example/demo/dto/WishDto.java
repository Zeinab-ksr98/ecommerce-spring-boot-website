package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WishDto {
    private Long id;
    private UserDto customer;
    private List<ProductDto> productList;
}
