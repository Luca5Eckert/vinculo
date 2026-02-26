package com.vinculo.module.person.domain.command;

public record GetNetworkCommand(
        String authenticatedPersonId,
        String personId
) {

    public static GetNetworkCommand of(String personId) {
        return new GetNetworkCommand(personId, personId);
    }

    public static GetNetworkCommand of(String authenticatedPersonId, String personId) {
        return new GetNetworkCommand(authenticatedPersonId, personId);
    }

}
