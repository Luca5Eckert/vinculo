package com.vinculo.module.person.controller.controller;

import com.vinculo.module.person.controller.dto.CreatePersonRequest;
import com.vinculo.module.person.controller.dto.GetAllPersonResponse;
import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.dto.UpdatePersonRequest;
import com.vinculo.module.person.controller.handler.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/persons")
@Tag(name = "Person", description = "Person management APIs")
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
    @Operation(summary = "Create a new person", description = "Creates a new person (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required", content = @Content)
    })
    public ResponseEntity<Void> create(@Validated @RequestBody CreatePersonRequest request){
        createPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a person", description = "Deletes a person by ID (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the person to delete") @PathVariable(value = "authorId") String personId){
        deletePersonHandler.handle(personId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{authorId}")
    @Operation(summary = "Get person by ID", description = "Retrieves person details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public ResponseEntity<PersonResponse> getById(
            @Parameter(description = "ID of the person to retrieve") @PathVariable String personId) {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        PersonResponse response = getPersonHandler.handle(authenticatedPersonId, personId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{authorId}")
    @Operation(summary = "Update person", description = "Updates person information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "ID of the person to update") @PathVariable String personId,
            @Validated @RequestBody UpdatePersonRequest request
    ) {
        updatePersonHandler.handle(personId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all persons", description = "Retrieves a paginated list of all persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persons retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAllPersonResponse.class)))
    })
    public ResponseEntity<List<GetAllPersonResponse>> getAll(
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size
    ) {
        var responses = getAllPersonHandler.handle(page, size);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/network")
    @Operation(summary = "Get my network", description = "Retrieves the network of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Network retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class)))
    })
    public ResponseEntity<List<PersonResponse>> getMyNetwork() {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        var responses = getMyNetworkHandler.handle(authenticatedPersonId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/network")
    @Operation(summary = "Get network by person ID", description = "Retrieves the network of a specific person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Network retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public ResponseEntity<List<PersonResponse>> getNetworkByPersonId(
            @Parameter(description = "ID of the person whose network to retrieve") @PathVariable String id) {
        var authenticatedPersonId = authenticationService.getAuthenticatedPersonId();

        var responses = getNetworkByPersonIdHandler.handle(authenticatedPersonId, id);

        return ResponseEntity.ok(responses);
    }

}
