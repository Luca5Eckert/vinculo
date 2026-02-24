package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.post.domain.command.GetAllByAuthorCommand;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllByAuthorUseCase {

    private final PostRepository postRepository;

    public GetAllByAuthorUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> execute(GetAllByAuthorCommand command){
        return postRepository.findAllByAuthorId(
                command.authorId(),
                command.limit(),
                command.skip()
        );
    }

}
