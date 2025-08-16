package com.joebank.transactionsservice.repository;

import com.joebank.transactionsservice.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IAccountRepository extends ReactiveCrudRepository<Account, Long> {
}
