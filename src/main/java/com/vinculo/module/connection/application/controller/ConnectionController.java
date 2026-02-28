package com.vinculo.module.connection.application.controller;

import com.vinculo.module.connection.application.dto.ConnectionResponse;
import com.vinculo.module.connection.application.handler.GetMyConnectionsHandler;
import com.vinculo.share.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/connections")
@Tag(name = "Connection", description = "Connection management APIs")
public class ConnectionController {

    private final GetMyConnectionsHandler getMyConnectionsHandler;

    private final AuthenticationService authenticationService;

    public ConnectionController(GetMyConnectionsHandler getMyConnectionsHandler, AuthenticationService authenticationService) {
        this.getMyConnectionsHandler = getMyConnectionsHandler;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get my connections", description = "Retrieves all connections for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connections retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConnectionResponse.class)))
    })
    public ResponseEntity<List<ConnectionResponse>> getMyConnections(){
        String personId = authenticationService.getAuthenticatedPersonId();

        var response = getMyConnectionsHandler.handle(personId);

        return ResponseEntity.ok(response);
    }

}
