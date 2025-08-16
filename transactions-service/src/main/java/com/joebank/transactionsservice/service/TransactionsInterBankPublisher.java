package com.joebank.transactionsservice.service;

import com.joebank.transactionsservice.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionsInterBankPublisher {
    @Value("${rabbitmq.queue.routing-key}")
    private String routingKey;
    @Value("${rabbitmq.queue.exchange}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public TransactionsInterBankPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTransactionCreatedEvent(Transaction transaction) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, transaction);
        System.out.println("Transaction created event published");
    }
}
