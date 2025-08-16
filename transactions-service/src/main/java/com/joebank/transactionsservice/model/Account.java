package com.joebank.transactionsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("cuentas")
public class Account {
    @Id
    private Long accountNumber;
    private LocalDateTime createdAt;
    private double balance;
    private Long bankId;

    public Account() {
    }

    public Account(Long accountNumber, LocalDateTime createdAt, double balance, Long bankId) {
        this.accountNumber = accountNumber;
        this.createdAt = createdAt;
        this.balance = balance;
        this.bankId = bankId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
