package com.example.demo.dto;

import com.example.demo.model.Address;
import com.example.demo.model.OnlineOrders;
import com.example.demo.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String password;

    private List<Role> roles;
    private String phone;
    private boolean deleted;

//    private List<OnlineOrdersDto> orders;
    private List<OnlineOrders> orders;

    private Long wishId;
    private Long cartId;
//    private List<AddressDto> addresses;
    private List<Address> addresses;
}
