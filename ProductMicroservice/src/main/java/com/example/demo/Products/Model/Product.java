package com.example.demo.Products.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Product")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name="productName",nullable = false)
    @NotNull
    public String productName;
    @Column(name="productDescription")
    public String productDescription;
    @Column(name="expirationDate",nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull
    public LocalDateTime expirationDate;
    @Column(name="manufacturingDate",nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull
    public LocalDateTime manufacturingDate;
    @Column(name="productCategory")
    public String productCategory;
    @Column(name="productQty")
    public int productQty;
}
