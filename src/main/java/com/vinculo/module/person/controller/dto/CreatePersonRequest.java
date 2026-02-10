package com.vinculo.module.person.controller.dto;

public record CreatePersonRequest(
        String name,
        String email,
        String phoneNumber,
        String password
) {
}
