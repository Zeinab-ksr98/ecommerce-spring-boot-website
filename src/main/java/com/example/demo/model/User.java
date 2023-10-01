package com.example.demo.model;

import com.example.demo.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private boolean enabled;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OnlineOrders> orders;

    @OneToOne
    private Wish wish;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany
    private List<Address> addresses;


    private String phone;

    public boolean deleted;

    public User(String username, String email, String password,String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone=phone;
        this.roles=new ArrayList<>();
        this.roles.add(Role.USER);
        this.cart=new Cart(getId());
        this.wish=new Wish();

    }
}