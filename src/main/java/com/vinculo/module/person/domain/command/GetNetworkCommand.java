package com.vinculo.module.person.domain.command;

public record GetNetworkCommand(
        Long authenticatedPersonId,
        Long personId
) {

    public static GetNetworkCommand of(Long personId) {
        return new GetNetworkCommand(personId, personId);
    }

    public static GetNetworkCommand of(Long authenticatedPersonId, Long personId) {
        return new GetNetworkCommand(authenticatedPersonId, personId);
    }

}
