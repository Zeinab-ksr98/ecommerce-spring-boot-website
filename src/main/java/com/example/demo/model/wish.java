package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private User customer;
    @ManyToMany
    List<Product> productList=new ArrayList<>();

    public wish() {
        productList = new ArrayList<>();
    }

    public wish(Long id, List<Product> productList) {
        this.id = id;
        this.productList = productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
