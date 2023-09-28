package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OnlineOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalPrice;
    @ManyToMany
    List<Product> productList=new ArrayList<>();

    @ManyToOne
    private User user;

    public OnlineOrders() {
        productList = new ArrayList<>();

    }

    public OnlineOrders(Long oid, List<Product> productList) {
        this.oid = oid;
        this.productList = productList;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getNumberOfProducts() {
        return this.productList.size();
    }

}
