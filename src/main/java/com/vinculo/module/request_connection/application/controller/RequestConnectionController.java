package com.vinculo.module.request_connection.application.controller;

import com.vinculo.module.request_connection.application.dto.RequestConnectionResponse;
import com.vinculo.module.request_connection.application.dto.SendRequestConnectionRequest;
import com.vinculo.module.request_connection.application.dto.UpdateStatusRequestConnectionRequest;
import com.vinculo.module.request_connection.application.handler.GetMyRequestConnectionsHandler;
import com.vinculo.module.request_connection.application.handler.SendRequestConnectionHandler;
import com.vinculo.module.request_connection.application.handler.UpdateStatusRequestConnectionHandler;
import com.vinculo.share.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/request-connections")
@Tag(name = "Request Connection", description = "Connection request management APIs")
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
    @Operation(summary = "Send connection request", description = "Sends a connection request to another person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Connection request sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Target person not found", content = @Content)
    })
    public ResponseEntity<Void> send(
            @Parameter(description = "ID of the person to send connection request to") @PathVariable(value = "personTargetId") String personTargetId,
            @Validated @RequestBody SendRequestConnectionRequest request
    ) {
        String personRequesterId = authenticationService.getAuthenticatedPersonId();

        sendRequestConnectionHandler.handle(personRequesterId, personTargetId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{requestConnectionId}")
    @Operation(summary = "Update connection request status", description = "Updates the status of a connection request (accept/reject)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Connection request status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Connection request not found", content = @Content)
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "ID of the connection request to update") @PathVariable(value = "requestConnectionId") String requestConnectionId,
            @Validated @RequestBody UpdateStatusRequestConnectionRequest request
    ) {
        String personTargetId = authenticationService.getAuthenticatedPersonId();

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
    @Operation(summary = "Get my connection requests", description = "Retrieves all connection requests for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connection requests retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestConnectionResponse.class)))
    })
    public ResponseEntity<List<RequestConnectionResponse>> getMyRequestConnections() {
        String personId = authenticationService.getAuthenticatedPersonId();

        var requestConnections = getMyRequestConnectionsHandler.handle(personId);

        return ResponseEntity.ok(requestConnections);
    }

}
