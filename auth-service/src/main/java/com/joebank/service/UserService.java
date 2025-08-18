package com.joebank.service;

import com.joebank.dto.AuthRequest;
import com.joebank.dto.AuthResponse;
import com.joebank.dto.RegisterRequest;
import com.joebank.model.User;
import com.joebank.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final IUserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authenticationManager;

    public UserService(IUserRepository repository, PasswordEncoder encoder, JwtService jwtService, ReactiveAuthenticationManager authenticationManager) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Mono<Object> register(RegisterRequest request) {
        return repository.findByUsername(request.username())
                .flatMap(existing -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(repository.save(new User(null, request.username(), encoder.encode(request.password()), request.role())));
    }

    public Mono<ResponseEntity<AuthResponse>> login(AuthRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        return authenticationManager.authenticate(authenticationToken)
                .map(authentication -> {
                    String token = jwtService.generateToken(authentication);
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
