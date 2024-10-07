package com.example.Order.Controller;

import com.example.Order.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/order")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrderService orderService;

//    @PostMapping()
//    public ResponseEntity<>
}
