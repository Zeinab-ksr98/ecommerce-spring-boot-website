package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String img;

    public Category() {
    }
    public Category(String cname, String img) {
        this.name= cname;
        this.img =img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
