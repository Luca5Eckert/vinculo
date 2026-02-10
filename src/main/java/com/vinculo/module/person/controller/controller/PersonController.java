package com.vinculo.module.person.controller.controller;

import com.vinculo.module.person.controller.dto.CreatePersonRequest;
import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.dto.UpdatePersonRequest;
import com.vinculo.module.person.controller.handler.CreatePersonHandler;
import com.vinculo.module.person.controller.handler.DeletePersonHandler;
import com.vinculo.module.person.controller.handler.GetPersonHandler;
import com.vinculo.module.person.controller.handler.UpdatePersonHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private final CreatePersonHandler createPersonHandler;
    private final DeletePersonHandler deletePersonHandler;
    private final GetPersonHandler getPersonHandler;
    private final UpdatePersonHandler updatePersonHandler;

    public PersonController(CreatePersonHandler createPersonHandler, DeletePersonHandler deletePersonHandler, GetPersonHandler getPersonHandler, UpdatePersonHandler updatePersonHandler) {
        this.createPersonHandler = createPersonHandler;
        this.deletePersonHandler = deletePersonHandler;
        this.getPersonHandler = getPersonHandler;
        this.updatePersonHandler = updatePersonHandler;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Validated CreatePersonRequest request){
        createPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{personId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable(value = "personId") Long personId){
        deletePersonHandler.handle(personId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonResponse> getById(@PathVariable Long personId) {
        PersonResponse response = getPersonHandler.handle(personId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Void> update(
            @PathVariable Long personId,
            @Validated @RequestBody UpdatePersonRequest request
    ) {
        updatePersonHandler.handle(personId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
