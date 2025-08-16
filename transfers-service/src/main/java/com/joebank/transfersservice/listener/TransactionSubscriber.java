package com.joebank.transfersservice.listener;

import com.joebank.transfersservice.model.Transaction;
import com.joebank.transfersservice.service.TransfersService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionSubscriber {
    private final TransfersService transfersService;

    public TransactionSubscriber(TransfersService transfersService) {
        this.transfersService = transfersService;
    }


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onTransactionCreated(Transaction transaction) {
        transfersService.processTransfer(transaction);
        System.out.println("Transaction processed: " + transaction);
    }
}
