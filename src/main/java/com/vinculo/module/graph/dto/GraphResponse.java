package com.vinculo.module.graph.dto;

import java.util.List;

public record GraphResponse(
        int mainNodeId,
        List<Node> nodes,
        List<Edge> edges
) {

}
