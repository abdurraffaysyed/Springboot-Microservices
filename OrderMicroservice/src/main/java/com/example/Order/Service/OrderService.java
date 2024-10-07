package com.example.Order.Service;

import com.example.Order.Model.Orders;
import com.example.Order.Repo.IOrderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final IOrderRepo orderRepo;
    @Transactional
    public void processOrder(Orders order){
        orderRepo.save(order);
    }
}
