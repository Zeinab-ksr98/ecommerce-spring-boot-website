package com.example.demo.repository;

import com.example.demo.model.OnlineOrders;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OnlineOrders, Long> {
    OnlineOrders findByUser(User user);
    List<OnlineOrders> findOrdersByUser(User user);

}
