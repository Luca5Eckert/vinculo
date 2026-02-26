package com.vinculo.module.person.controller.dto;

public record PersonResponse(
        Long id,
        String name,
        String username,
        String email,
        String phoneNumber,
        boolean isConnection
) {
}
