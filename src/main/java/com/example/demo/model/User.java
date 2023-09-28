package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "users")
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Email
    @Column(nullable = false, unique = true)
    public String email;
    @Column(nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;
    public boolean deleted;
    @OneToMany
    private List<OnlineOrders> orders;
    private long wishId;
    private Long cartId;
    private String phone;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;
    public boolean getDeleted() {
        return deleted;
    }

    public User() {
    }

    public User(String email, String password, List<Role> roles, Cart cart){
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.deleted = false;
        this.orders = new ArrayList<>();

    }
}