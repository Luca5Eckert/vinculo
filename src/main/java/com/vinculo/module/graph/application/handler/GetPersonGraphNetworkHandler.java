package com.vinculo.module.graph.application.handler;

import com.vinculo.module.graph.application.dto.GraphResponse;
import com.vinculo.module.graph.application.mapper.GraphMapper;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;
import com.vinculo.module.graph.domain.use_case.GetGraphNetworkUseCase;
import org.springframework.stereotype.Component;

@Component
public class GetPersonGraphNetworkHandler {

    private final GetGraphNetworkUseCase getGraphNetworkUseCase;

    private final GraphMapper mapper;

    public GetPersonGraphNetworkHandler(GetGraphNetworkUseCase getGraphNetworkUseCase, GraphMapper mapper) {
        this.getGraphNetworkUseCase = getGraphNetworkUseCase;
        this.mapper = mapper;
    }

    public GraphResponse handle(String personId, String personAuthenticatedId){
        var query = GetGraphNetworkQuery.of(personId, personAuthenticatedId);

        var graph = getGraphNetworkUseCase.execute(query);

        return mapper.toGraphResponse(graph, personId);
    }

}
