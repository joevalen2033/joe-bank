package com.joebank.accountsservice.repository;

import com.joebank.accountsservice.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IAccountRepository extends ReactiveCrudRepository<Account, Long> {
    Mono<Account> findByAccountNumber(Long accountNumber);
}
