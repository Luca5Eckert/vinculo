package com.vinculo.module.person.controller.controller;

import com.vinculo.module.person.controller.dto.CreatePersonRequest;
import com.vinculo.module.person.controller.handler.CreatePersonHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private final CreatePersonHandler createPersonHandler;

    public PersonController(CreatePersonHandler createPersonHandler) {
        this.createPersonHandler = createPersonHandler;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Validated CreatePersonRequest request){
        createPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
