package com.example.demo.service;


import com.example.demo.model.Wish;
import com.example.demo.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getAllWishs(){
        return wishRepository.findAll();
    }

    public Wish getWishById(Long id){
        return wishRepository.findById(id).orElse(null);
    }

    public Wish createWish(Wish wish){
        return wishRepository.save(wish);
    }

    public Wish updateWish(Wish wish){
        return wishRepository.save(wish);
    }

    public void deleteWish(Long id){
        wishRepository.deleteById(id );
    }
}
