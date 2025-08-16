package com.joebank.accountsservice.grpc;

import com.joebank.accountsservice.dto.TransactionsDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class TransactionsConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionsConsumer.class);
    @Value("${transactions.grpc.host}")
    private String grpcHost="localhost";
    @Value("${transactions.grpc.port}")
    private int grpcPort=9090;

    private final ManagedChannel channel= ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
            .usePlaintext()
            .build();

    private final TransactionsServiceGrpc.TransactionsServiceBlockingStub stub=
            TransactionsServiceGrpc.newBlockingStub(channel);

    public Flux<TransactionsDTO> getTransactions( Long accountNumber) {
        log.info(accountNumber.toString().concat(" - Sending gRPC request for transactions"));
        TransactionRequest request = TransactionRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        TransactionResponse response = stub.getTransactions(request);
        //log.info("Received gRPC response for account number: " + response.getAccount());
        return Flux.fromIterable(response.getTransactionsList())
                .map(tx -> new TransactionsDTO(
                        tx.getAccount(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getIsInterbank(),
                        tx.getCreatedAt(),
                        tx.getTaxAmount()
                ));
    }
}
