package com.joebank.accountsservice.service;

import com.joebank.accountsservice.dto.AccountDTO;
import com.joebank.accountsservice.dto.TransactionsDTO;
import com.joebank.accountsservice.grpc.TransactionsConsumer;
import com.joebank.accountsservice.model.Account;
import com.joebank.accountsservice.repository.IAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AccountsService {
    private static final Logger log = LoggerFactory.getLogger(AccountsService.class);
    private final IAccountRepository accountRepository;
    private final BanksService banksService;
    private final TransactionsConsumer transactionsConsumer;

    public AccountsService(IAccountRepository accountRepository, BanksService banksService, TransactionsConsumer transactionsConsumer) {
        this.accountRepository = accountRepository;
        this.banksService = banksService;
        this.transactionsConsumer = transactionsConsumer;
    }

    public Mono<Account> createAccount(AccountDTO account) {
        return banksService.getBank(account.getBankId())
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + account.getBankId())))
                .flatMap(bank -> {
                    log.info(account.getAccountNumber()+" "+account.getBalance());
                    Account newAccount = new Account();
                    newAccount.setAccountNumber(account.getAccountNumber());
                    newAccount.setBankId(bank.getId());
                    newAccount.setBalance(account.getBalance());
                    newAccount.setCreatedAt(LocalDateTime.now());
                    return accountRepository.save(newAccount);
                })
                .onErrorResume(error -> Mono.error(new RuntimeException("Failed to create account: " + error.getMessage())));
    }

    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll()
                .switchIfEmpty(Flux.error(new RuntimeException("No accounts found")));
    }

    public Mono<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found with ID: " + accountId)));
    }

    public Mono<Account> updateAccount(Long accountNumber, AccountDTO account) {
        return accountRepository.findByAccountNumber(accountNumber)
                .flatMap(existingAccount -> {
                    existingAccount.setBalance(account.getBalance());
                    //existingAccount.setUpdatedAt(LocalDateTime.now());
                    return accountRepository.save(existingAccount);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found for update with ID: " + accountNumber)));
    }

    public Flux<TransactionsDTO> getTransactionsByAccountNumber(Long accountNumber) {
        return transactionsConsumer.getTransactions(accountNumber)
                .switchIfEmpty(Flux.error(new RuntimeException("No transactions found for account number: " + accountNumber)));
    }
}
