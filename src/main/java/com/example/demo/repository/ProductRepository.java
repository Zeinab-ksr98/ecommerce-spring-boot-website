package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where name = ?1", nativeQuery = true)
    Optional<Product> findProductByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE  p.Deleted = false")
    List<Product> findAllProducts();
    @Query("SELECT p FROM Product p WHERE  p.Deleted = false AND p.seller.id =?1")
    List<Product> findAllProductsForSeller(@Param("sellerId") UUID sellerId);
    @Query("SELECT p FROM Product p WHERE  p.Deleted = false AND p.seller.id =?1 ORDER BY p.availability DESC")
    List<Product> SortAllProductsForSeller(@Param("sellerId") UUID sellerId);



    //and the following will be used mainly by on the customer side
    @Query("SELECT p FROM Product p WHERE p.Deleted = false AND p.category.id =?1")
    List<Product> FilterAllProductsByCategory(@Param("categoryId") Long categoryId);;


    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.availability = true) " +
            "AND (p.Deleted = false) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.availability = true) " +
            "AND (p.Deleted = false)")
    List<Product> searchByNameOrDescription(@Param("keyword") String keyword);
}
