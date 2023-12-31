package com.example.demo.model;

import com.example.demo.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OnlineOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalPrice;

    @ManyToMany
    private List<CartItem> ordersList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
