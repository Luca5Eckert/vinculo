package com.vinculo.module.graph.dto;

public record Edge(
        Long source,
        Long target,
        String typeConnection,
        double weight
) {
}
