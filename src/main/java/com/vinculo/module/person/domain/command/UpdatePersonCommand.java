package com.vinculo.module.person.domain.command;

public record UpdatePersonCommand(
        Long personId,
        String name,
        String phoneNumber
) {
}
