package com.joebank.accountsservice.service;

import com.joebank.accountsservice.dto.GetBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BanksService {

    private final WebClient.Builder webClientBuilder;
    private static final Logger log = LoggerFactory.getLogger(BanksService.class);

    @Value("${banks-service.url}")
    private String banksServiceUrl;

    public BanksService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<GetBank> getBank(Long bankId) {
        log.info(banksServiceUrl.concat("/").concat(bankId.toString()));
        return webClientBuilder
                .build()
                .get()
                .uri(banksServiceUrl + "/" + bankId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Bank not found in Banks Service")))
                .bodyToMono(GetBank.class);
    }

}
