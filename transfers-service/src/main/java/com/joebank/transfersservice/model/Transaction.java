package com.joebank.transfersservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transacciones")
public class Transaction {
    @Id
    private Long id;
    private Long accountNumber;
    private String type; // DEPOSITO, RETIRO
    private double amount; // default 0.0
    private boolean isInterbank; // default false
    private String createdAt;
    private double taxAmount; // Optional, can be null

    public Transaction() {
    }

    public Transaction(Long id, Long accountNumber, Long acoountNumberDestination, String type, double amount, boolean isInterbank, String createdAt, double taxAmount) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", isInterbank=" + isInterbank +
                ", createdAt='" + createdAt + '\'' +
                ", taxAmount=" + taxAmount +
                '}';
    }
}
