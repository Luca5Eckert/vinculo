package com.vinculo.module.auth.application.controller;

import com.vinculo.module.auth.application.dto.LoginRequest;
import com.vinculo.module.auth.application.dto.RegisterPersonRequest;
import com.vinculo.module.auth.application.handler.LoginHandler;
import com.vinculo.module.auth.application.handler.RegisterPersonHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final RegisterPersonHandler registerPersonHandler;
    private final LoginHandler loginHandler;

    public AuthController(RegisterPersonHandler registerPersonHandler, LoginHandler loginHandler) {
        this.registerPersonHandler = registerPersonHandler;
        this.loginHandler = loginHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new person", description = "Creates a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<Void> register(@Validated @RequestBody RegisterPersonRequest request) {
        registerPersonHandler.handle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", 
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequest request) {
         String token = loginHandler.handle(request);

        return ResponseEntity
                .ok(token);
    }


}
