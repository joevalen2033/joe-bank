package com.joebank.transactionsservice.repository;

import com.joebank.transactionsservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    //Mono<Transaction> findByAccountNumber(Long account);
    Flux<Transaction> findTransactionsByAccountNumber(Long accountNumber);
}
