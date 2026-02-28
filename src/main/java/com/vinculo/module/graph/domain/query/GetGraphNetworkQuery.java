package com.vinculo.module.graph.domain.query;

public record GetGraphNetworkQuery(
        String userId
) {
    public static GetGraphNetworkQuery of(String personId) {
        return new GetGraphNetworkQuery(personId);
    }

}
