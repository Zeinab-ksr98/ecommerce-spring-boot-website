package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishService wishService;

    public Optional<User> findUserByUserName(String username){
        return userRepository.findUserByUserName(username);
    }
    public Optional<User> findUserByEmailAndUserName(String username,String email){
        return userRepository.findUserByEmailAndName(email,username);
    }
//    public User findUserByWishId(long wid){
//        return userRepository.findUserByWishId(wid);
//    }
    public User save(User user){
        return userRepository.save(user);
    }
    public Boolean userNameExists(String username){ return userRepository.existsByUsername(username);}

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Boolean userEmailExists(String email){ return userRepository.existsByEmail(email);}

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<User> getAllUsersNotBlocked(){
        return userRepository.findAllNotBlocked();
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user, boolean isAdmin){
        User newUser = new User(user.getUsername(), user.email, passwordEncoder.encode(user.getPassword()), user.getPhone(), isAdmin);
        wishService.save(newUser.getWish());
        cartService.save(newUser.getCart());
        return userRepository.save(newUser);
    }
    public User BlockUser(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setDeleted(true));
        return user.orElse(null);
    }
    public User deActivateUser(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(false));
        return user.orElse(null);
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserInfoDetails) {
            // Assuming you have a custom UserDetails implementation called UserPrincipal
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            return userInfoDetails.getUser();
        }

        return null;
    }
    public User activate(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(true));
        return user.orElse(null);
    }

    public boolean validId(UserDetails userDetails, String id){
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        return userInfoDetails.getUserId().equals(id);
    }


}