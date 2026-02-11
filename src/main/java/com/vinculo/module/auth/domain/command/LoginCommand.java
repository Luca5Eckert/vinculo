package com.vinculo.module.auth.domain.command;

public record LoginCommand(
        String email,
        String password
) {
}
