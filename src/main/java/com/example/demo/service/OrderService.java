package com.example.demo.service;


import com.example.demo.dto.OnlineOrdersDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.OnlineOrders;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public OnlineOrdersDto mapToDto(OnlineOrders order){
        return new OnlineOrdersDto(order.getId(),order.getStatus(), order.getTotalPrice(), order.getProductList());
    }
    public List<OnlineOrdersDto> mapToDtoList(List<OnlineOrders> orders){
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
