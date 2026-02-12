package com.vinculo.module.request_connection.domain.service;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.strategy.RequestStatusStrategy;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConnectionStrategyManager {

    private final List<RequestStatusStrategy> strategies;

    public ConnectionStrategyManager(List<RequestStatusStrategy> strategies) {
        this.strategies = strategies;
    }

    public void process(RequestConnection request) {
        strategies.stream()
                .filter(s -> s.supports(request.getStatus()))
                .findFirst()
                .ifPresent(s -> s.execute(request));
    }
}