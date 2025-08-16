package com.joebank.transfersservice.service;

import com.joebank.transfersservice.model.Transaction;
import com.joebank.transfersservice.repository.ITransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransfersService {
    private static final Logger log = LoggerFactory.getLogger(TransfersService.class);
    private final ITransferRepository transferRepository;

    @Value("${transfers-service.tax-rate}")
    private double taxRate;

    public TransfersService(ITransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void processTransfer(Transaction transaction) {
        transaction.setTaxAmount(taxRate);
        transaction.setAmount(transaction.getAmount()* (1 - taxRate));
        //log.info(transaction.getAccountNumber().toString().concat(" - Transfer processed with tax rate: ").concat(String.valueOf(taxRate)));
        transferRepository.save(transaction)
                .log(transaction.getAccountNumber().toString().concat(" - Transfer processed with tax rate: ").concat(String.valueOf(taxRate)))
                .subscribe();
    }
}
