package com.vinculo.module.connection.application.controller;

import com.vinculo.module.connection.application.dto.ConnectionResponse;
import com.vinculo.module.connection.application.dto.CreateConnectionRequest;
import com.vinculo.module.connection.application.handler.CreateConnectionHandler;
import com.vinculo.module.connection.application.handler.GetMyConnectionsHandler;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/connections")
public class ConnectionController {

    private final GetMyConnectionsHandler getMyConnectionsHandler;

    private final AuthenticationService authenticationService;

    public ConnectionController(GetMyConnectionsHandler getMyConnectionsHandler, AuthenticationService authenticationService) {
        this.getMyConnectionsHandler = getMyConnectionsHandler;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<ConnectionResponse>> getMyConnections(){
        long personId = authenticationService.getAuthenticatedPersonId();

        var response = getMyConnectionsHandler.handle(personId);

        return ResponseEntity.ok(response);
    }

}
