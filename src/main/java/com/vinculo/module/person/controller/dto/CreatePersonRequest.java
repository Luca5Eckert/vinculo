package com.vinculo.module.person.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String phoneNumber,
        @NotBlank String password
) {
}
