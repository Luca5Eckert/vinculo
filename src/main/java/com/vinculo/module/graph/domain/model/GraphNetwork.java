package com.vinculo.module.graph.domain.model;

import java.util.List;

public record GraphNetwork(
        List<Node> nodes,
        List<Edge> edges
) {
}
