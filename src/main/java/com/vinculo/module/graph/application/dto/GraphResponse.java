package com.vinculo.module.graph.application.dto;

import java.util.List;

public record GraphResponse(
        String mainNodeId,
        List<NodeResponse> nodes,
        List<EdgeResponse> edgeResponses
) {

}
