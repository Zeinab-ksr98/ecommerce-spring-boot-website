package com.example.demo.service;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getAllProductsForSeller(UUID sellerId){
        return productRepository.findAllProductsForSeller(sellerId);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAllProducts();
    }
    public List<Product> SortAllProductsForSeller(UUID sellerId) {

        return productRepository.SortAllProductsForSeller(sellerId);
    }
    //for customer
    public List<Product> FilterAllProductsByCategory(Long categoryId) {
        return productRepository.FilterAllProductsByCategory(categoryId);
    }


    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        Product product = getProductById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByNameOrDescription(keyword);
    }
}
