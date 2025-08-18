package com.joebank.transactionsservice.service;

import com.joebank.transactionsservice.dto.TransactionDTO;
import com.joebank.transactionsservice.model.Account;
import com.joebank.transactionsservice.model.Transaction;
import com.joebank.transactionsservice.model.TransactionType;
import com.joebank.transactionsservice.repository.IAccountRepository;
import com.joebank.transactionsservice.repository.ITransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.time.LocalDateTime;

@Service
public class TransactionsService {
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;
    private final TransactionsInterBankPublisher transactionsInterBankPublisher;

    public TransactionsService(ITransactionRepository transactionRepository, IAccountRepository accountRepository, TransactionsInterBankPublisher transactionsInterBankPublisher) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionsInterBankPublisher = transactionsInterBankPublisher;
    }


    public Mono<Transaction> createTransaction(TransactionDTO transactionDTO) {
        return accountRepository.findById(transactionDTO.getSourceAccount())
                .switchIfEmpty(Mono.error(new RuntimeException("Source Account not found: " + transactionDTO.getSourceAccount())))
                .flatMap(sourceAccount -> {

                    return accountRepository.findById(transactionDTO.getDestinationAccount())
                        .switchIfEmpty(Mono.error(new RuntimeException("Destination Account not found: " + transactionDTO.getDestinationAccount())))
                        .flatMap(destinationAccount -> {
                            if (transactionDTO.getAmount() > sourceAccount.getBalance()) {
                                return Mono.error(new RuntimeException("Insufficient funds in source account: " + transactionDTO.getSourceAccount()));
                            }
                            if (transactionDTO.getAmount() < 0) {
                                return Mono.error(new RuntimeException("Deposit amount must be positive: " + transactionDTO.getAmount()));
                            }
                            // Additional validation can be added here
                            // If all validations pass, proceed with the transaction
                            Transaction sourceTransaction = new Transaction();
                            Transaction destinationTransaction = new Transaction();
                            sourceTransaction.setAccountNumber(transactionDTO.getSourceAccount());
                            sourceTransaction.setType(TransactionType.RETIRO);
                            sourceTransaction.setAmount(transactionDTO.getAmount());
                            sourceTransaction.setCreatedAt(LocalDateTime.now().toString());
                            destinationTransaction.setType(TransactionType.DEPOSITO);
                            destinationTransaction.setAccountNumber(transactionDTO.getDestinationAccount());
                            destinationTransaction.setAmount(transactionDTO.getAmount());
                            destinationTransaction.setCreatedAt(LocalDateTime.now().toString());
                            if(sourceAccount.getBankId().equals(destinationAccount.getBankId())) {
                                sourceTransaction.setInterbank(false);
                                destinationTransaction.setInterbank(false);
                            } else {
                                sourceTransaction.setInterbank(true);
                                destinationTransaction.setInterbank(true);
                            }
                            // Save the updated accounts
                            return transactionRepository.save(sourceTransaction)
                                    .then(Mono.defer(() -> {
                                        if (!sourceTransaction.isInterbank()) {
                                            return transactionRepository.save(destinationTransaction);
                                        } else {
                                            transactionsInterBankPublisher.publishTransactionCreatedEvent(destinationTransaction);
                                            return Mono.empty();
                                        }
                                    }))
                                    .flatMap(savedTransaction -> {
                                        sourceAccount.setBalance(sourceAccount.getBalance() - transactionDTO.getAmount());
                                        return accountRepository.save(sourceAccount)
                                                .flatMap(savedAccount -> {
                                                    destinationAccount.setBalance(destinationAccount.getBalance() + transactionDTO.getAmount());
                                                    return accountRepository.save(destinationAccount)
                                                            .thenReturn(savedTransaction);
                                                })
                                                .thenReturn(savedTransaction);
                                    });
                                //.thenReturn(sourceTransaction);
                        });
                });
    }

    public Flux<Transaction> findTransactionsByAccountNumber(Long accountNumber) {
        return transactionRepository.findTransactionsByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction not found: " + accountNumber)));
    }
}
