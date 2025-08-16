package com.joebank.accountsservice.dto;

public class TransactionsDTO {
    private Long accountNumber;
    private String type; // DEPOSITO, RETIRO
    private double amount; // default 0.0
    private boolean isInterbank; // default false
    private String createdAt;
    private double taxAmount; // Optional, can be null

    public TransactionsDTO() {
    }

    public TransactionsDTO( Long accountNumber, String type, double amount, boolean isInterbank, String createdAt, double taxAmount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.isInterbank = isInterbank;
        this.createdAt = createdAt;
        this.taxAmount = taxAmount;
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
}
