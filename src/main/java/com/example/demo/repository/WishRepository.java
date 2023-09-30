package com.example.demo.repository;

import com.example.demo.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Modifying
    @Query("DELETE FROM Wish w WHERE w.id = :cartId AND :productId MEMBER OF w.productList")
    void deleteProductFromWish(@Param("cartId") Long cartId, @Param("productId") Long productId);

}
