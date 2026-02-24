package com.vinculo.module.post.application.controller;

import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.handler.CreatePostHandler;
import com.vinculo.module.post.application.handler.DeletePostHandler;
import com.vinculo.module.post.application.handler.GetAllForPersonHandler;
import com.vinculo.share.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final AuthenticationService authenticationService;

    private final CreatePostHandler createPostHandler;
    private final DeletePostHandler deletePostHandler;
    private final GetAllForPersonHandler getAllForPersonHandler;

    public PostController(AuthenticationService authenticationService, CreatePostHandler createPostHandler, DeletePostHandler deletePostHandler, GetAllForPersonHandler getAllForPersonHandler) {
        this.authenticationService = authenticationService;
        this.createPostHandler = createPostHandler;
        this.deletePostHandler = deletePostHandler;
        this.getAllForPersonHandler = getAllForPersonHandler;
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


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable long postId
    ) {
        var personId = authenticationService.getAuthenticatedPersonId();

        deletePostHandler.handle(postId, personId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getForPerson(
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "10") int limit
    ){
        var personId = authenticationService.getAuthenticatedPersonId();

        var response = getAllForPersonHandler.handle(personId, limit, skip);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persons/{authorId}")
    public ResponseEntity<List<PostResponse>> getByPerson(
            @PathVariable(name = "userId") long personId
    ) {
        return null;
    }



}
