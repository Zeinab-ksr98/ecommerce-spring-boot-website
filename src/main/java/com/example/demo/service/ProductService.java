package com.example.demo.service;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //for the admin
    public List<Product> getAllProducts(){
        return productRepository.findAllProducts();
    }
    public List<Product> sortAllProducts() {
        return productRepository.SortAllProductsByAvailability();
    }
    //for customer
    public List<Product> getAllAvailableProducts() {
        return productRepository.findAllAvailableProducts();
    }

    public List<Product> FilterAllAvailableProductsByCategory(Long categoryId) {
        return productRepository.FilterAllAvailableProductsByCategory(categoryId);
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
