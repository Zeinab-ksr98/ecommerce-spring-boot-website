package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.model.Wish;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final UserRepository userRepository;


    @Autowired
    public WishService(WishRepository wishRepository, UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
    }

    public Wish getWishById(Long id){
        return wishRepository.findById(id).orElse(null);
    }
//    public User getUserByWishId(Long id){
//        return userRepository.findUserByWishId(id);
//    }

    public Wish save(Wish wish){
        return wishRepository.save(wish);
    }

    //no need for it now
    public List<Wish> getAllWishs(){
        return wishRepository.findAll();
    }
    public void deleteWish(Long id){
        wishRepository.deleteById(id );
    }
}
