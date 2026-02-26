package com.vinculo.module.person.domain.command;

public record CreatePersonCommand(
        String name,
        String username,
        String phoneNumber,
        String email,
        String password
) {
}
