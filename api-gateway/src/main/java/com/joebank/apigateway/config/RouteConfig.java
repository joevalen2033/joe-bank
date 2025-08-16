package com.joebank.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Value("${banks-service.url}")
    private String urlBanks;
    @Value("${banks-service.path}")
    private String pathBanks;
    @Value("${banks-service.id}")
    private String idBanks;

    @Value("${accounts-service.url}")
    private String urlAccounts;
    @Value("${accounts-service.path}")
    private String pathAccounts;
    @Value("${accounts-service.id}")
    private String idAccounts;

    @Value("${transactions-service.url}")
    private String urltTransactions;
    @Value("${transactions-service.path}")
    private String pathTransactions;
    @Value("${transactions-service.id}")
    private String idTransactions;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(idBanks, r -> r.path(pathBanks).uri(urlBanks))
                .route(idAccounts, route -> route.path(pathAccounts).uri(urlAccounts))
                .route(idTransactions, route -> route.path(pathTransactions).uri(urltTransactions))
                .build();
    }
}
