package com.example.demo.Products.Repository;

import com.example.demo.Products.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByProductNameAsc();
    Product findByProductName(String productName);

}
