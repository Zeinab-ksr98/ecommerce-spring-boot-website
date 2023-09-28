package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private double price;
    private double discount;
    private String urlimg;
    public boolean availability=true;
    public boolean Deleted;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;



    public Product() {
    }

    public Product(Long id, String name, double price, String description, String urlimg) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = 0;
        this.description = description;
        this.urlimg = urlimg;
    }

    public Product(Long id, String name, Category category, double price, double discount, String description, String urlimg, boolean availability, User seller) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.urlimg = urlimg;
        this.availability = availability;
        this.seller = seller;
    }

    public boolean isitavailability() {
        return availability;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }

  public void setAvailability(boolean availability) {
        this.availability = availability;
    }


}
