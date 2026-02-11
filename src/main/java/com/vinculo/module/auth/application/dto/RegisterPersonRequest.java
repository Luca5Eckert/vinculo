package com.vinculo.module.auth.application.dto;

public record RegisterPersonRequest(
        String name,
        String email,
        String phoneNumber,
        String password
) {
}
