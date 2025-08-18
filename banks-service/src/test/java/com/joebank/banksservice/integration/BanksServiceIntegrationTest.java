package com.joebank.banksservice.integration;

import com.joebank.banksservice.model.Bank;
import com.joebank.banksservice.repository.IBankRepository;
import com.joebank.banksservice.service.BanksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@ActiveProfiles("test")
class BanksServiceIntegrationTest {

    @Autowired
    private BanksService banksService;

    @Autowired
    private IBankRepository bankRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void setUp() {
        bankRepository.deleteAll().block();
    }

    @Test
    void createBank() {
        Bank bank = new Bank();
        bank.setName("Integration Bank");
        bank.setLocation("Integration City");

        banksService.create(bank).block();
    }

    @Test
    void updateBank() {
        Bank bank = new Bank();
        bank.setName("Old Name");
        bank.setLocation("Old City");
        Bank savedBank = bankRepository.save(bank).block();

        Bank updated = new Bank();
        updated.setName("New Name");
        updated.setLocation("New City");

    }

    @Test
    void getAllBanks() {
        Bank bank1 = new Bank();
        bank1.setName("Bank1");
        bank1.setLocation("City1");
        Bank bank2 = new Bank();
        bank2.setName("Bank2");
        bank2.setLocation("City2");
        bankRepository.saveAll(Flux.just(bank1, bank2)).collectList().block();

    }
}
