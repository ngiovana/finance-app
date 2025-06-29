package com.example.FinanceBackend.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String token;
    private Long userId;

    public AuthResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}
