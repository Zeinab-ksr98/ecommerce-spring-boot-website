package com.example.demo.security;


import com.example.demo.helpers.ValidationHelper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        Optional<User> user;
        if(ValidationHelper.isValidEmail(credential))
            user = userService.findUserByEmail(credential);
        else
            user = userService.findUserByUserName(credential);
        return user.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("The user credential is not found"));
    }
}