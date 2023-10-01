package com.example.demo.model;

import com.example.demo.service.UserService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<CartItem> cartItemList;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(UUID userid) {
        this.user =UserService.getUserById(userid) ;
    }
}
