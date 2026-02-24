package com.vinculo.module.post.application.handler;

import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.mapper.PostMapper;
import com.vinculo.module.post.domain.command.GetAllForPersonCommand;
import com.vinculo.module.post.domain.use_case.GetAllForPersonUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllForPersonHandler {

    private final GetAllForPersonUseCase getAllForPersonUseCase;

    private final PostMapper postMapper;

    public GetAllForPersonHandler(GetAllForPersonUseCase getAllForPersonUseCase, PostMapper postMapper) {
        this.getAllForPersonUseCase = getAllForPersonUseCase;
        this.postMapper = postMapper;
    }

    public List<PostResponse> handle(long personId, int limit, int skip){
        var command = GetAllForPersonCommand.of(personId, limit, skip);

        var posts = getAllForPersonUseCase.execute(command);

        return posts.stream()
                .map(postMapper::of)
                .toList();
    }

}
