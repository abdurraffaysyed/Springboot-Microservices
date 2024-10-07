package com.example.Order.Controller;

import com.example.Order.Model.Orders;
import com.example.Order.Service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/order")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid Orders order) throws Exception{
        try{
            orderService.processOrder(order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("The exception is being raised while handling the request of createOrder", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
