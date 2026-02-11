package com.vinculo.module.auth.application.dto;

public record LoginRequest(
        String email,
        String password
) {
}
