package com.joebank.transactionsservice.controller;

import com.joebank.transactionsservice.dto.TransactionDTO;
import com.joebank.transactionsservice.model.Transaction;
import com.joebank.transactionsservice.service.TransactionsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/transfer/{sourceAccountId}")
    public Mono<Transaction> createTransaction(@PathVariable Long sourceAccountId, @RequestBody TransactionDTO transaction) {
        transaction.setSourceAccount(sourceAccountId);
        return transactionsService.createTransaction(transaction);
    }

    @GetMapping("/{accountNumber}")
    public Flux<Transaction> getTransactionsByAccountNumber(@PathVariable Long accountNumber) {
        return transactionsService.findTransactionsByAccountNumber(accountNumber);
    }
}
