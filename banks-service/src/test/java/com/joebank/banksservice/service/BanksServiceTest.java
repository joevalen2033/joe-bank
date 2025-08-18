package com.joebank.banksservice.service;

import com.joebank.banksservice.model.Bank;
import com.joebank.banksservice.repository.IBankRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BanksServiceTest {

    @Test
    void create() {
        IBankRepository repository = Mockito.mock(IBankRepository.class);
        BanksService service = new BanksService(repository);

        Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("Test Bank");
        bank.setLocation("Test City");

        Mockito.when(repository.save(bank)).thenReturn(Mono.just(bank));

        Mono<Bank> result = service.create(bank);

        StepVerifier.create(result)
                .expectNext(bank)
                .verifyComplete();

        Mockito.verify(repository).save(bank);
    }

    @Test
    void getAll() {
        IBankRepository repository = Mockito.mock(IBankRepository.class);
        BanksService service = new BanksService(repository);

        Bank bank1 = new Bank();
        bank1.setId(1L);
        bank1.setName("Bank One");
        bank1.setLocation("City One");

        Bank bank2 = new Bank();
        bank2.setId(2L);
        bank2.setName("Bank Two");
        bank2.setLocation("City Two");

        Mockito.when(repository.findAll()).thenReturn(Flux.just(bank1, bank2));

        reactor.test.StepVerifier.create(service.getAll())
                .expectNext(bank1)
                .expectNext(bank2)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

    @Test
    void update() {
        IBankRepository repository = Mockito.mock(IBankRepository.class);
        BanksService service = new BanksService(repository);

        Long bankId = 1L;
        Bank existingBank = new Bank();
        existingBank.setId(bankId);
        existingBank.setName("Old Name");
        existingBank.setLocation("Old Location");

        Bank updatedBank = new Bank();
        updatedBank.setName("New Name");
        updatedBank.setLocation("New Location");

        Bank savedBank = new Bank();
        savedBank.setId(bankId);
        savedBank.setName("New Name");
        savedBank.setLocation("New Location");

        Mockito.when(repository.findById(bankId)).thenReturn(Mono.just(existingBank));
        Mockito.when(repository.save(existingBank)).thenReturn(Mono.just(savedBank));

        Mono<Bank> result = service.update(bankId, updatedBank);

        reactor.test.StepVerifier.create(result)
                .expectNext(savedBank)
                .verifyComplete();

        Mockito.verify(repository).findById(bankId);
        Mockito.verify(repository).save(existingBank);
    }
}