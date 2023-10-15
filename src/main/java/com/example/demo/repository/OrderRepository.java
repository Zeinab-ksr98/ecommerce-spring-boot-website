package com.example.demo.repository;

import com.example.demo.model.OnlineOrders;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OnlineOrders, Long> {
    OnlineOrders findByUser(User user);
    List<OnlineOrders> findOrdersByUser(User user);
    @Query("SELECT o FROM OnlineOrders o WHERE o.user.id =?1")
    List<OnlineOrders> findAllOrdersForCustomer(@Param("custumerId") UUID customerId);
}
