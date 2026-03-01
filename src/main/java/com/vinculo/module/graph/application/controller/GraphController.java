package com.vinculo.module.graph.application.controller;

import com.vinculo.module.graph.application.dto.GraphResponse;
import com.vinculo.module.graph.application.handler.GetGraphNetworkHandler;
import com.vinculo.share.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/graphs")
@Tag(name = "Graph", description = "Network graph APIs")
public class GraphController {

    private final GetGraphNetworkHandler getGraphNetworkHandler;

    private final AuthenticationService authenticationService;

    public GraphController(GetGraphNetworkHandler getGraphNetworkHandler, AuthenticationService authenticationService) {
        this.getGraphNetworkHandler = getGraphNetworkHandler;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get my network graph", description = "Retrieves the network graph for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Network graph retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GraphResponse.class)))
    })
    public ResponseEntity<GraphResponse> getFeed(){
        var personId = authenticationService.getAuthenticatedPersonId();

        var response = getGraphNetworkHandler.handle(personId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{personId}")
    @Operation(summary = "Get network graph for person", description = "Retrieves the network graph for a specific person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Network graph retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GraphResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public ResponseEntity<GraphResponse> getGraphForPerson(
            @Parameter(description = "ID of the person whose network graph to retrieve") @PathVariable String personId){
        var response = getGraphNetworkHandler.handle(personId);

        return ResponseEntity.ok(response);
    }


}
