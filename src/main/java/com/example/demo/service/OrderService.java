package com.example.demo.service;


import com.example.demo.model.OnlineOrders;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository OrderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.OrderRepository = orderRepository;
    }

    public List<OnlineOrders> getAllOrders(){
        return OrderRepository.findAll();
    }

    public OnlineOrders getOrderById(Long id){
        return OrderRepository.findById(id).orElse(null);
    }

    public OnlineOrders createOrder(OnlineOrders product){
        return OrderRepository.save(product);
    }

    public OnlineOrders updateOrder(OnlineOrders product){
        return OrderRepository.save(product);
    }

    public void deleteOrder(Long id){
        OrderRepository.deleteById(id );
    }
}
