package com.vinculo.module.connection.application.controller;

import com.vinculo.module.connection.application.dto.CreateConnectionRequest;
import com.vinculo.module.connection.application.handler.CreateConnectionHandler;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/connections")
public class ConnectionController {

    private final CreateConnectionHandler createConnectionHandler;

    private final AuthenticationService authenticationService;

    public ConnectionController(CreateConnectionHandler createConnectionHandler, AuthenticationService authenticationService) {
        this.createConnectionHandler = createConnectionHandler;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(CreateConnectionRequest request){
        long personId = authenticationService.getAuthenticatedPersonId();

        createConnectionHandler.handle(request, personId);

        return ResponseEntity.ok().build();
    }

}
