package com.joebank.transfersservice.repository;

import com.joebank.transfersservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ITransferRepository extends ReactiveCrudRepository<Transaction, Long> {
}
