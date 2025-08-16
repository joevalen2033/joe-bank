package com.joebank.banksservice.repository;

import com.joebank.banksservice.model.Bank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankRepository extends ReactiveCrudRepository<Bank, Long> {
    Flux<Bank> findByName(String name);
    //Flux<Bank> findByLocation(String location);
}
