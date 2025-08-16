package com.joebank.transactionsservice.grpc;

import com.joebank.transactionsservice.repository.ITransactionRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;


@GrpcService
public class TransactionsProvider extends TransactionsServiceGrpc.TransactionsServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(TransactionsProvider.class);
    // Here you would inject your repository or service to handle transactions
    private final ITransactionRepository repository;

    public TransactionsProvider(ITransactionRepository repository) {
        this.repository = repository;
    }

    /*@Override
    public void getTransactions(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        log.info(String.valueOf(request.getAccountNumber()).concat(" - Received gRPC request for transactions"));
        repository.findTransactionsByAccountNumber(request.getAccountNumber())
                .switchIfEmpty(Mono.error(new RuntimeException("No transactions found for account number: " + request.getAccountNumber())))
                .subscribe(transaction -> {
                    log.info(transaction.getAccountNumber().toString().concat(" - Sending gRPC response for transactions"));
                    TransactionResponse response = TransactionResponse
                            .newBuilder()
                            .setAmount(transaction.getAccountNumber())
                            .setType(transaction.getType().toString())
                            .setAmount(transaction.getAmount())
                            .setIsInterbank(transaction.isInterbank())
                            .setCreatedAt(transaction.getCreatedAt())
                            .setTaxAmount(transaction.getTaxAmount())
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                }, responseObserver::onError).dispose();
    }*/

    @Override
    public void getTransactions(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        log.info(request.getAccountNumber() + " - Received gRPC request for transactions");
        repository.findTransactionsByAccountNumber(request.getAccountNumber())
                .map(transaction -> Transaction.newBuilder()
                        .setAccount(transaction.getAccountNumber())
                        .setType(transaction.getType().toString())
                        .setAmount(transaction.getAmount())
                        .setIsInterbank(transaction.isInterbank())
                        .setCreatedAt(transaction.getCreatedAt())
                        .setTaxAmount(transaction.getTaxAmount())
                        .build())
                .collectList()
                .subscribe(
                        transactionsList -> {
                            TransactionResponse response = TransactionResponse.newBuilder()
                                    .addAllTransactions(transactionsList)
                                    .build();
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        responseObserver::onError
                );
    }
}
