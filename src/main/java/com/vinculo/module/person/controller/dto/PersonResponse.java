package com.vinculo.module.person.controller.dto;

public record PersonResponse(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
}
