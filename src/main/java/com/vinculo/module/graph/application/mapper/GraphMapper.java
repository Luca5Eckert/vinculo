package com.vinculo.module.graph.application.mapper;

import com.vinculo.module.graph.application.dto.EdgeResponse;
import com.vinculo.module.graph.application.dto.GraphResponse;
import com.vinculo.module.graph.application.dto.NodeResponse;
import com.vinculo.module.graph.domain.model.Edge;
import com.vinculo.module.graph.domain.model.GraphNetwork;
import com.vinculo.module.graph.domain.model.Node;
import org.springframework.stereotype.Component;

@Component
public class GraphMapper {

    public GraphResponse toGraphResponse(GraphNetwork graphNetwork, String mainNode) {
        var nodes = graphNetwork.nodes().stream()
                .map(this::toNodeResponse)
                .toList();

        var edges = graphNetwork.edges().stream()
                .map(this::toEdgeResponse)
                .toList();

        return new GraphResponse(
                mainNode,
                nodes,
                edges
        );
    }

    private NodeResponse toNodeResponse(Node node) {
        return new NodeResponse(
                node.id(),
                node.name(),
                node.username()
        );
    }

    private EdgeResponse toEdgeResponse(Edge edge) {
        return new EdgeResponse(
                edge.source(),
                edge.target(),
                edge.typeConnection(),
                edge.weight()
        );
    }
}
