package com.example.Order.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id", nullable = false)
    @NotNull
    private Long productId;
    @Column(name = "product_qty", nullable = false)
    @NotNull
    private int productQty;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "order_creation_date")
    private final LocalDateTime orderCreationDate = LocalDateTime.now();
    @Column(name = "order_shipment_date", nullable = false)
    private LocalDateTime orderShipmentDate;
}
