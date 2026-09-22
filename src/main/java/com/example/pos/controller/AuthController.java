package com.example.pos.controller;

import com.example.pos.dto.request.LoginRequest;
import com.example.pos.dto.request.RegisterRequest;
import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.TokenResponse;
import com.example.pos.entity.User;
import com.example.pos.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        User user = authService.register(request);

        Map<String, Object> data = Map.of(
                "username", user.getUsername(),
                "role", user.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Registration successful",
                                data
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {

        String token = authService.login(request);

        TokenResponse response = TokenResponse.builder()
                .token(token)
                .build();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }
}