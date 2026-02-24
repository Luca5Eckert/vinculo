package com.vinculo.module.post.application.controller;

import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.handler.CreatePostHandler;
import com.vinculo.share.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final AuthenticationService authenticationService;

    private final CreatePostHandler createPostHandler;

    public PostController(AuthenticationService authenticationService, CreatePostHandler createPostHandler) {
        this.authenticationService = authenticationService;
        this.createPostHandler = createPostHandler;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(
            @Valid @RequestBody CreatePostRequest createPostRequest
    ) {
        var personId = authenticationService.getAuthenticatedPersonId();

        var response = createPostHandler.handle(createPostRequest, personId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
