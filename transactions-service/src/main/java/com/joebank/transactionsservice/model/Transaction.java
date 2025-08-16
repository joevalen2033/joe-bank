package com.joebank.transactionsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transacciones")
public class Transaction {
    @Id
    private Long id;
    private Long accountNumber;
    private TransactionType type; // DEPOSITO, RETIRO
    private double amount; // default 0.0
    private boolean isInterbank; // default false
    private String createdAt;
    private double taxAmount; // Optional, can be null

    public Transaction() {
    }

    public Transaction(Long id, Long accountNumber, Long acoountNumberDestination, TransactionType type, double amount, boolean isInterbank, String createdAt, double taxAmount) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.isInterbank = isInterbank;
        this.createdAt = createdAt;
        this.taxAmount = taxAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumberSource) {
        this.accountNumber = accountNumberSource;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isInterbank() {
        return isInterbank;
    }

    public void setInterbank(boolean interbank) {
        isInterbank = interbank;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
}
