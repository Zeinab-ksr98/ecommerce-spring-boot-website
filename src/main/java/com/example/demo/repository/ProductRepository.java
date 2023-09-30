package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where name = ?1", nativeQuery = true)
    Optional<Product> findProductByName(String name);

    boolean existsByName(String name);
    //originaly we have find all
    //but in our case we need only the non deleted ones
    //used mainly for the admin

    //i must add the condition of seller id
    @Query("SELECT p FROM Product p WHERE  p.Deleted = false")
    List<Product> findAllProducts();
    @Query("SELECT p FROM Product p WHERE  p.Deleted = false ORDER BY p.availability DESC")
    List<Product> SortAllProductsByAvailability();



    //and the following will be used mainly by on the customer side
    @Query("SELECT p FROM Product p WHERE p.availability = true AND p.Deleted = false")
    List<Product> findAllAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.availability = true AND p.Deleted = false AND p.category.id =?1")
    List<Product> FilterAllAvailableProductsByCategory(@Param("categoryId") Long categoryId);;


    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.availability = true) " +
            "AND (p.Deleted = false) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.availability = true) " +
            "AND (p.Deleted = false)")
    List<Product> searchByNameOrDescription(@Param("keyword") String keyword);
}
