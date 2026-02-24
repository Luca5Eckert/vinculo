package com.vinculo.module.post.application.controller;

import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.handler.CreatePostHandler;
import com.vinculo.module.post.application.handler.DeletePostHandler;
import com.vinculo.share.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final AuthenticationService authenticationService;

    private final CreatePostHandler createPostHandler;
    private final DeletePostHandler deletePostHandler;

    public PostController(AuthenticationService authenticationService, CreatePostHandler createPostHandler, DeletePostHandler deletePostHandler) {
        this.authenticationService = authenticationService;
        this.createPostHandler = createPostHandler;
        this.deletePostHandler = deletePostHandler;
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


    @DeleteMapping({"{postId}"})
    public ResponseEntity<Void> delete(
            @PathVariable long postId
    ) {
        var personId = authenticationService.getAuthenticatedPersonId();

        deletePostHandler.handle(postId, personId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
