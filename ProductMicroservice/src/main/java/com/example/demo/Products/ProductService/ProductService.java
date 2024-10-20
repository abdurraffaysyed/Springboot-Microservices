package com.example.demo.Products.ProductService;

import com.example.demo.Products.Model.Product;
import com.example.demo.Products.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepo _repo;
    @Transactional
    public Product upsertProduct(Product prod){
        return _repo.save(prod);
    }

    public List<Product> getProducts(){
        return _repo.findAll(Sort.by(Sort.Direction.ASC, "productName"));
    }

    public Optional<Product> getProductById(Long id){
        return _repo.findById(id);
    }

    public Product getProductByName(String productName)
    {
        return _repo.findByProductName(productName);
    }
    @Transactional
    public void deleteProduct(Long id) throws Exception
    {
        _repo.deleteById(id);
    }
    @Transactional
    public void updateProductFields(Long id, String productName){
        Optional<Product> product = _repo.findById(id);
        product.ifPresent(prod -> prod.productName = productName);
    }

    @RabbitListener(queues = "sample_queue")
    public void consumeMessage(String message)
    {
        String x = message;
    }
}
