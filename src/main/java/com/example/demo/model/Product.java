package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public double price;
    public double discount;
    public String urlImg;
    public boolean availability;
    public boolean Deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User seller;


}
