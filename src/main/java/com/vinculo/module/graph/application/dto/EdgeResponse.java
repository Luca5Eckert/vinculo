package com.vinculo.module.graph.application.dto;

public record EdgeResponse(
        String source,
        String target,
        String typeConnection,
        double weight
) {
}
