package com.vinculo.module.graph.domain.use_case;

import com.vinculo.module.graph.domain.exception.GraphException;
import com.vinculo.module.graph.domain.model.GraphNetwork;
import com.vinculo.module.graph.domain.policy.GraphVisibilityPolicy;
import com.vinculo.module.graph.domain.port.GraphRepository;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;
import org.springframework.stereotype.Component;

@Component
public class GetGraphNetworkUseCase {

    private final GraphRepository graphRepository;

    private final GraphVisibilityPolicy graphVisibilityPolicy;

    public GetGraphNetworkUseCase(GraphRepository graphRepository, GraphVisibilityPolicy graphVisibilityPolicy) {
        this.graphRepository = graphRepository;
        this.graphVisibilityPolicy = graphVisibilityPolicy;
    }

    public GraphNetwork execute(GetGraphNetworkQuery query) {
        if(!graphVisibilityPolicy.canView(query.personId(), query.personAuthenticated())){
            throw new GraphException("You don't have access to this person's graph network");
        }

        return graphRepository.execute(query);
    }

}
