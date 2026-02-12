package com.vinculo.module.request_connection.domain.strategy;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;

public interface RequestStatusStrategy {
    boolean supports(StatusRequestConnection status);
    void execute(RequestConnection request);
}