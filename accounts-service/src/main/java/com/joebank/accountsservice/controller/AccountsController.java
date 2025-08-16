package com.joebank.accountsservice.controller;

import com.joebank.accountsservice.dto.AccountDTO;
import com.joebank.accountsservice.dto.TransactionsDTO;
import com.joebank.accountsservice.model.Account;
import com.joebank.accountsservice.service.AccountsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping()
    public Mono<Account> createAccount(@RequestBody AccountDTO account) {
        return accountsService.createAccount(account);
    }

    @GetMapping("/{accountId}")
    public Mono<Account> getAccountById(@PathVariable Long accountId) {
        return accountsService.getAccountById(accountId);
    }

    @GetMapping
    public Flux<Account> getAllAccounts() {
        return accountsService.getAllAccounts();
    }

    @PutMapping("/{accountId}")
    public Mono<Account> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account) {
        return accountsService.updateAccount(accountId, account);
    }

    @GetMapping("/transactions/{accountNumber}")
    public Flux<TransactionsDTO> getAccountTransactions(@PathVariable Long accountNumber) {
        return accountsService.getTransactionsByAccountNumber(accountNumber);
    }
}
