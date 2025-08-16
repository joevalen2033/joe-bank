package com.joebank.banksservice.service;

import com.joebank.banksservice.model.Bank;
import com.joebank.banksservice.repository.IBankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BanksService {
    private final IBankRepository repository;

    public BanksService(IBankRepository repository) {
        this.repository = repository;
    }


    public Flux<Bank> getAll() {
        return repository.findAll();
    }

    public Mono<Bank> getById(Long bankId) {
        return repository
                .findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
    }

    public Flux<Bank> getByName(String name) {
        return repository.findAll()
                .filter(bank -> bank.getName().equalsIgnoreCase(name))
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with name: " + name)));
    }



    public Mono<Bank> create(Bank bank) {
        return repository.save(bank);
    }

    public Mono<Bank> update(Long bankId, Bank bank) {
        return repository.findById(bankId)
                .flatMap(existingBank -> {
                    existingBank.setName(bank.getName());
                    existingBank.setLocation(bank.getLocation());
                    return repository.save(existingBank);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found for update")));
    }
}
