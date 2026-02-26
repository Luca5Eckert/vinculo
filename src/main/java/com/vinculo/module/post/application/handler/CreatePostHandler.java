package com.vinculo.module.post.application.handler;

import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.mapper.PostMapper;
import com.vinculo.module.post.domain.command.CreatePostCommand;
import com.vinculo.module.post.domain.use_case.CreatePostUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePostHandler {

    private final CreatePostUseCase createPostUseCase;

    private final PostMapper mapper;

    public CreatePostHandler(CreatePostUseCase createPostUseCase, PostMapper mapper) {
        this.createPostUseCase = createPostUseCase;
        this.mapper = mapper;
    }

    @Transactional
    public PostResponse handle(CreatePostRequest request, String personId){
        var command = CreatePostCommand.of(request.content(), personId);

        var post = createPostUseCase.execute(command);

        return mapper.of(post);
    }
}
