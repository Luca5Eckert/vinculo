package com.vinculo.module.post.application.handler;

import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.mapper.PostMapper;
import com.vinculo.module.post.domain.command.GetAllByAuthorCommand;
import com.vinculo.module.post.domain.use_case.GetAllByAuthorUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllByAuthorHandler {

    private final GetAllByAuthorUseCase getAllByAuthorUseCase;

    private final PostMapper postMapper;

    public GetAllByAuthorHandler(GetAllByAuthorUseCase getAllByAuthorUseCase, PostMapper postMapper) {
        this.getAllByAuthorUseCase = getAllByAuthorUseCase;
        this.postMapper = postMapper;
    }

    public List<PostResponse> handle(long authorId, int limit, int skip){
        var command = GetAllByAuthorCommand.of(authorId, limit, skip);

        var posts = getAllByAuthorUseCase.execute(command);

        return posts.stream()
                .map(postMapper::of)
                .toList();
    }
}
