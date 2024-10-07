package com.example.Order.Service;

import com.example.Order.Model.Order;
import com.example.Order.Repo.IOrderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final IOrderRepo orderRepo;
    @Transactional
    public void processOrder(Order order){
        orderRepo.save(order);
    }
}
