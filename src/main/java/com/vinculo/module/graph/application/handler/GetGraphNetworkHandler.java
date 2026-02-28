package com.vinculo.module.graph.application.handler;

import com.vinculo.module.graph.application.dto.GraphResponse;
import com.vinculo.module.graph.application.mapper.GraphMapper;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;
import com.vinculo.module.graph.domain.use_case.GetGraphNetworkUseCase;
import org.springframework.stereotype.Component;

@Component
public class GetGraphNetworkHandler {

    private final GetGraphNetworkUseCase getGraphNetworkUseCase;

    private final GraphMapper graphMapper;

    public GetGraphNetworkHandler(GetGraphNetworkUseCase getGraphNetworkUseCase, GraphMapper graphMapper) {
        this.getGraphNetworkUseCase = getGraphNetworkUseCase;
        this.graphMapper = graphMapper;
    }

    public GraphResponse handle(String personId) {
        var query = GetGraphNetworkQuery.of(personId);

        var graphNetwork = getGraphNetworkUseCase.execute(query);

        return graphMapper.toGraphResponse(graphNetwork, personId);
    }


}
