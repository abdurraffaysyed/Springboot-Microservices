package com.example.demo.Products.Controller;

import com.example.demo.Products.Model.Product;
import com.example.demo.Products.ProductService.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product API Documentation")
public class ProductController {
    private static final Logger _logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService _productService;

    @PostMapping
    @Operation(summary = "Create Product", description = "Create a new product")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody Product product){
        try{
            Product prod = _productService.upsertProduct(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            _logger.error("Exception on Product Controller", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get products")
    public ResponseEntity<List<Product>> getProducts(){
        try{
            return ResponseEntity.ok().body(_productService.getProducts());
        }
        catch (Exception ex){
            _logger.error("Exception on Product Controller while fetching all products", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product by Id")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws IllegalStateException{
        Product product = _productService.getProductById(id).orElseThrow(IllegalStateException::new);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/productName")
    @Operation(summary = "Get product by name")
    public ResponseEntity<Product> getProductByName(@RequestParam(required = true, defaultValue = "bodylotion", name = "name") String productName)
    {
        Product product = _productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }

    @PutMapping
    @Operation(summary = "Update the product")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product){
        Product prod = _productService.upsertProduct(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete the product")
    public ResponseEntity<Void> deleteProduct(@RequestParam(required = true, name = "productId") Long id)
    {
        try{
            _productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/updateProductFields")
    @Operation(summary = "Update specific fields of the product")
    public ResponseEntity<Void> updateProductFields(@RequestParam(name = "productId") Long id, @RequestParam(name = "productName") String productName)
    {
        try{
            _productService.updateProductFields(id, productName);
            return ResponseEntity.ok().build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
}
