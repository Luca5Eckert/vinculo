package com.vinculo.module.graph.domain.model;

public record Edge(
        String source,
        String target,
        String typeConnection,
        double weight
) {
}
