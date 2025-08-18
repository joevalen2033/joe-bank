package com.joebank.controller;

import com.joebank.dto.AuthRequest;
import com.joebank.dto.AuthResponse;
import com.joebank.dto.RegisterRequest;
import com.joebank.service.JwtService;
import com.joebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService, ReactiveAuthenticationManager authenticationManager) {
        this.userService = userService;
    }

    @PostMapping("")
    public Mono<ResponseEntity<Object>> login(@RequestBody AuthRequest request) {
        return userService.login(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Object>> register(@RequestBody RegisterRequest request) {
        return userService.register(request)
                .map(ResponseEntity::ok);
    }
}
