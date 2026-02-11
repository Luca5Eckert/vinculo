package com.vinculo.module.auth.domain.command;

public record RegisterPersonCommand(
        String name,
        String email,
        String phoneNumber,
        String password
) {
}
