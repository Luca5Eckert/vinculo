package com.vinculo.module.post.application.controller;

import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.handler.CreatePostHandler;
import com.vinculo.module.post.application.handler.DeletePostHandler;
import com.vinculo.module.post.application.handler.GetAllByAuthorHandler;
import com.vinculo.module.post.application.handler.GetAllForPersonHandler;
import com.vinculo.share.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/posts")
@Tag(name = "Post", description = "Post management APIs")
public class PostController {

    private final AuthenticationService authenticationService;

    private final CreatePostHandler createPostHandler;
    private final DeletePostHandler deletePostHandler;
    private final GetAllForPersonHandler getAllForPersonHandler;
    private final GetAllByAuthorHandler getAllByAuthorHandler;

    public PostController(AuthenticationService authenticationService, CreatePostHandler createPostHandler, DeletePostHandler deletePostHandler, GetAllForPersonHandler getAllForPersonHandler, GetAllByAuthorHandler getAllByAuthorHandler) {
        this.authenticationService = authenticationService;
        this.createPostHandler = createPostHandler;
        this.deletePostHandler = deletePostHandler;
        this.getAllForPersonHandler = getAllForPersonHandler;
        this.getAllByAuthorHandler = getAllByAuthorHandler;
    }

    @PostMapping
    @Operation(summary = "Create a new post", description = "Creates a new post for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
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
    @Operation(summary = "Delete a post", description = "Deletes a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Not the post owner", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the post to delete") @PathVariable String postId
    ) {
        var personId = authenticationService.getAuthenticatedPersonId();

        deletePostHandler.handle(postId, personId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    @Operation(summary = "Get posts for authenticated user", description = "Retrieves posts for the authenticated user's feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)))
    })
    public ResponseEntity<List<PostResponse>> getForPerson(
            @Parameter(description = "Number of posts to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Maximum number of posts to return") @RequestParam(defaultValue = "10") int limit
    ){
        var personId = authenticationService.getAuthenticatedPersonId();

        var response = getAllForPersonHandler.handle(personId, limit, skip);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{authorId}")
    @Operation(summary = "Get posts by author", description = "Retrieves posts created by a specific author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<List<PostResponse>> getByPerson(
            @Parameter(description = "ID of the author") @PathVariable(name = "authorId") String authorId,
            @Parameter(description = "Number of posts to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Maximum number of posts to return") @RequestParam(defaultValue = "10") int limit
    ) {
        var personAuthenticatedId = authenticationService.getAuthenticatedPersonId();

        var response = getAllByAuthorHandler.handle(
                personAuthenticatedId,
                authorId,
                limit,
                skip);

        return ResponseEntity.ok(response);
    }


}
