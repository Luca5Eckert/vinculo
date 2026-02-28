package com.vinculo.module.graph.application.controller;

import com.vinculo.module.graph.application.dto.GraphResponse;
import com.vinculo.module.graph.application.handler.GetGraphNetworkHandler;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/graphs")
public class GraphController {

    private final GetGraphNetworkHandler getGraphNetworkHandler;

    private final AuthenticationService authenticationService;

    public GraphController(GetGraphNetworkHandler getGraphNetworkHandler, AuthenticationService authenticationService) {
        this.getGraphNetworkHandler = getGraphNetworkHandler;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    public ResponseEntity<GraphResponse> getFeed(){
        var personId = authenticationService.getAuthenticatedPersonId();

        var response = getGraphNetworkHandler.handle(personId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<GraphResponse> getGraphForPerson(String personId){
        var response = getGraphNetworkHandler.handle(personId);

        return ResponseEntity.ok(response);
    }


}
