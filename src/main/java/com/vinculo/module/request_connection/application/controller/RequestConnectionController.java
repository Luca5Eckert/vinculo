package com.vinculo.module.request_connection.application.controller;

import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.request_connection.application.dto.SendRequestConnectionRequest;
import com.vinculo.module.request_connection.application.handler.SendRequestConnectionHandler;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/request-connections")
public class RequestConnectionController {

    private final SendRequestConnectionHandler sendRequestConnectionHandler;

    private final AuthenticationService authenticationService;

    public RequestConnectionController(SendRequestConnectionHandler sendRequestConnectionHandler, AuthenticationService authenticationService) {
        this.sendRequestConnectionHandler = sendRequestConnectionHandler;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{personTargetId}")
    public ResponseEntity<Void> send(
            @PathVariable(value = "personTargetId") Long personTargetId,
            @Validated @RequestBody SendRequestConnectionRequest request
            ) {
        long personRequesterId = authenticationService.getAuthenticatedPersonId();

        sendRequestConnectionHandler.handle(personRequesterId, personTargetId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
