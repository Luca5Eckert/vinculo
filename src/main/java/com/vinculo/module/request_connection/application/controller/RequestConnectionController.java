package com.vinculo.module.request_connection.application.controller;

import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.request_connection.application.dto.RequestConnectionResponse;
import com.vinculo.module.request_connection.application.dto.SendRequestConnectionRequest;
import com.vinculo.module.request_connection.application.dto.UpdateStatusRequestConnectionRequest;
import com.vinculo.module.request_connection.application.handler.GetMyRequestConnectionsHandler;
import com.vinculo.module.request_connection.application.handler.SendRequestConnectionHandler;
import com.vinculo.module.request_connection.application.handler.UpdateStatusRequestConnectionHandler;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/request-connections")
public class RequestConnectionController {

    private final SendRequestConnectionHandler sendRequestConnectionHandler;
    private final UpdateStatusRequestConnectionHandler updateStatusRequestConnectionHandler;
    private final GetMyRequestConnectionsHandler getMyRequestConnectionsHandler;

    private final AuthenticationService authenticationService;

    public RequestConnectionController(SendRequestConnectionHandler sendRequestConnectionHandler, UpdateStatusRequestConnectionHandler updateStatusRequestConnectionHandler, GetMyRequestConnectionsHandler getMyRequestConnectionsHandler, AuthenticationService authenticationService) {
        this.sendRequestConnectionHandler = sendRequestConnectionHandler;
        this.updateStatusRequestConnectionHandler = updateStatusRequestConnectionHandler;
        this.getMyRequestConnectionsHandler = getMyRequestConnectionsHandler;
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

    @PutMapping("/{requestConnectionId}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "requestConnectionId") Long requestConnectionId,
            UpdateStatusRequestConnectionRequest request
    ) {
        long personTargetId = authenticationService.getAuthenticatedPersonId();

        updateStatusRequestConnectionHandler.handle(
                requestConnectionId,
                personTargetId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<RequestConnectionResponse>> getMyRequestConnections() {
        long personId = authenticationService.getAuthenticatedPersonId();

        var requestConnections = getMyRequestConnectionsHandler.handle(personId);

        return ResponseEntity.ok(requestConnections);
    }

}
