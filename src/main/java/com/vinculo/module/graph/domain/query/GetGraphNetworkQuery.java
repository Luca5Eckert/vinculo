package com.vinculo.module.graph.domain.query;

public record GetGraphNetworkQuery(
        String personId,
        String personAuthenticated
) {
    public static GetGraphNetworkQuery of(String personId) {
        return new GetGraphNetworkQuery(personId, personId);
    }

    public static GetGraphNetworkQuery of(String personId, String personAuthenticated) {
        return new GetGraphNetworkQuery(personId, personAuthenticated);
    }

}
