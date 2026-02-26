package com.vinculo.module.person.domain.command;

public record UpdatePersonCommand(
        String personId,
        String name,
        String phoneNumber
) {
}
