package com.example.Order.Service;

import com.example.Order.Model.Orders;
import com.example.Order.Repo.IOrderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    @Autowired
    private Queue queue;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final IOrderRepo orderRepo;
    @Transactional
    public void processOrder(Orders order){
        orderRepo.save(order);
    }

    public void generateMessage(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
