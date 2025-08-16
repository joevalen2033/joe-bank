package com.joebank.banksservice.controller;

import com.joebank.banksservice.model.Bank;
import com.joebank.banksservice.service.BanksService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banks")
public class BanksController {

    private final BanksService banksService;

    public BanksController(BanksService banksService) {
        this.banksService = banksService;
    }

    @GetMapping
    public Flux<Bank> getAllBanks() {
        return banksService.getAll();
    }

    @GetMapping("/{bankId}")
    public Mono<Bank> getBankById(@PathVariable Long bankId) {
        return banksService.getById(bankId);
    }

    @GetMapping("/name/{name}")
    public Flux<Bank> getBankByName(@PathVariable String name) {
        return banksService.getByName(name);
    }

    @PostMapping
    public Mono<Bank> createBank(@RequestBody Bank bank) {
        return banksService.create(bank);
    }

    @PutMapping("/{bankId}")
    public Mono<Bank> updateBank(@PathVariable Long bankId, @RequestBody Bank bank) {
        return banksService.update(bankId, bank);
    }
}
