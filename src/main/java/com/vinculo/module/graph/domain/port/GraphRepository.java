package com.vinculo.module.graph.domain.port;

import com.vinculo.module.graph.domain.model.GraphNetwork;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;

public interface GraphRepository {
    GraphNetwork getGraphNetwork(String personId);

    GraphNetwork execute(GetGraphNetworkQuery query);
}
