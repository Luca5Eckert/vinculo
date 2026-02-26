package com.vinculo.module.person.controller.dto;

public record PersonResponse(
        String id,
        String name,
        String username,
        String email,
        String phoneNumber,
        boolean isConnection
) {
}
