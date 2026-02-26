package com.vinculo.module.person.controller.controller;

import com.vinculo.module.person.controller.dto.CreatePersonRequest;
import com.vinculo.module.person.controller.dto.GetAllPersonResponse;
import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.dto.UpdatePersonRequest;
import com.vinculo.module.person.controller.handler.*;
import com.vinculo.share.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private final CreatePersonHandler createPersonHandler;
    private final DeletePersonHandler deletePersonHandler;
    private final GetPersonHandler getPersonHandler;
    private final UpdatePersonHandler updatePersonHandler;
    private final GetAllPersonHandler getAllPersonHandler;
    private final GetMyNetworkHandler getMyNetworkHandler;
    private final GetNetworkByPersonIdHandler getNetworkByPersonIdHandler;

    private final AuthenticationService authenticationService;

    public PersonController(CreatePersonHandler createPersonHandler, DeletePersonHandler deletePersonHandler, GetPersonHandler getPersonHandler, UpdatePersonHandler updatePersonHandler, GetAllPersonHandler getAllPersonHandler, GetMyNetworkHandler getMyNetworkHandler, GetNetworkByPersonIdHandler getNetworkByPersonIdHandler, AuthenticationService authenticationService) {
        this.createPersonHandler = createPersonHandler;
        this.deletePersonHandler = deletePersonHandler;
        this.getPersonHandler = getPersonHandler;
        this.updatePersonHandler = updatePersonHandler;
        this.getAllPersonHandler = getAllPersonHandler;
        this.getMyNetworkHandler = getMyNetworkHandler;
        this.getNetworkByPersonIdHandler = getNetworkByPersonIdHandler;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@Validated @RequestBody CreatePersonRequest request){
        createPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable(value = "authorId") String personId){
        deletePersonHandler.handle(personId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<PersonResponse> getById(@PathVariable String personId) {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        PersonResponse response = getPersonHandler.handle(authenticatedPersonId, personId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Void> update(
            @PathVariable String personId,
            @Validated @RequestBody UpdatePersonRequest request
    ) {
        updatePersonHandler.handle(personId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<GetAllPersonResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var responses = getAllPersonHandler.handle(page, size);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/network")
    public ResponseEntity<List<PersonResponse>> getMyNetwork() {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        var responses = getMyNetworkHandler.handle(authenticatedPersonId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/network")
    public ResponseEntity<List<PersonResponse>> getNetworkByPersonId(@PathVariable String id) {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        var responses = getNetworkByPersonIdHandler.handle(authenticatedPersonId, id);

        return ResponseEntity.ok(responses);
    }

}
