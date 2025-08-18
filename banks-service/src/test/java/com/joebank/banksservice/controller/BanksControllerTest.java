
package com.joebank.banksservice.controller;

import com.joebank.banksservice.model.Bank;
import com.joebank.banksservice.service.BanksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BanksControllerTest {

    private BanksService banksService;
    private BanksController banksController;

    @BeforeEach
    void setUp() {
        banksService = Mockito.mock(BanksService.class);
        banksController = new BanksController(banksService);
    }

    @Test
    void getAllBanks() {
        Bank bank1 = new Bank();
        bank1.setId(1L);
        bank1.setName("Bank One");
        bank1.setLocation("City One");

        Bank bank2 = new Bank();
        bank2.setId(2L);
        bank2.setName("Bank Two");
        bank2.setLocation("City Two");

        Mockito.when(banksService.getAll()).thenReturn(Flux.just(bank1, bank2));

        StepVerifier.create(banksController.getAllBanks())
                .expectNext(bank1)
                .expectNext(bank2)
                .verifyComplete();

        Mockito.verify(banksService).getAll();
    }

    @Test
    void createBank() {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("Test Bank");
        bank.setLocation("Test City");

        Mockito.when(banksService.create(bank)).thenReturn(Mono.just(bank));

        StepVerifier.create(banksController.createBank(bank))
                .expectNext(bank)
                .verifyComplete();

        Mockito.verify(banksService).create(bank);
    }

    @Test
    void updateBank() {
        Long bankId = 1L;
        Bank bank = new Bank();
        bank.setName("Updated Name");
        bank.setLocation("Updated City");

        Bank updatedBank = new Bank();
        updatedBank.setId(bankId);
        updatedBank.setName("Updated Name");
        updatedBank.setLocation("Updated City");

        Mockito.when(banksService.update(bankId, bank)).thenReturn(Mono.just(updatedBank));

        StepVerifier.create(banksController.updateBank(bankId, bank))
                .expectNext(updatedBank)
                .verifyComplete();

        Mockito.verify(banksService).update(bankId, bank);
    }
}
