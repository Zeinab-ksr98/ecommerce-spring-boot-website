package com.example.demo.service;


import com.example.demo.model.Address;
import com.example.demo.model.CartItem;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address){
        return addressRepository.save(address);
    }

}
