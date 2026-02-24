package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.post.domain.command.GetAllForPersonCommand;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllForPersonUseCase {

    private final PostRepository postRepository;

    public GetAllForPersonUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> execute(GetAllForPersonCommand command){
        return postRepository.findNetworkFeed(
                command.personId(),
                command.limit(),
                command.skip()
        );
    }

}
