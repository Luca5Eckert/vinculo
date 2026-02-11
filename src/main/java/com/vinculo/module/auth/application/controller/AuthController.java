package com.vinculo.module.auth.application.controller;

import com.vinculo.module.auth.application.dto.RegisterPersonRequest;
import com.vinculo.module.auth.application.handler.RegisterPersonHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final RegisterPersonHandler registerPersonHandler;

    public AuthController(RegisterPersonHandler registerPersonHandler) {
        this.registerPersonHandler = registerPersonHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody RegisterPersonRequest request) {
        registerPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


}
