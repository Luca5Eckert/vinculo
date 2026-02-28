package com.vinculo.module.graph.domain.use_case;

import com.vinculo.module.graph.domain.model.GraphNetwork;
import com.vinculo.module.graph.domain.port.GraphRepository;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;
import org.springframework.stereotype.Component;

@Component
public class GetGraphNetworkUseCase {

    private final GraphRepository graphRepository;

    public GetGraphNetworkUseCase(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public GraphNetwork execute(GetGraphNetworkQuery query) {
        return graphRepository.execute(query);
    }

}
