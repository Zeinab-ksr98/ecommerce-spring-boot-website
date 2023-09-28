package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private CategoryDto category;
    private double price;
    private double discount;
    private String urlimg;
    private boolean availability;
    private boolean Deleted;
    private UserDto seller;
}
