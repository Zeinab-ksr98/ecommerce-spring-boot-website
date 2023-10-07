package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is required")
    private Category category;

    private double price;
    private double discount;
    private String urlimg;
    public boolean availability=true;
    public boolean Deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

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
