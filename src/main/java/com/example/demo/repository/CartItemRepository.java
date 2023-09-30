package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select * from cart_item where user_id = ?1", nativeQuery = true)
    List<CartItem> findCartItemsByUserId(UUID id);
}
