package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.post.domain.command.DeletePostCommand;
import com.vinculo.module.post.domain.exception.PostDomainException;
import com.vinculo.module.post.domain.exception.PostNotFoundException;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletePostUseCase {

    private final PostRepository postRepository;

    public DeletePostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void execute(DeletePostCommand command){
        Post post = postRepository.findById(command.postId())
                .orElseThrow(PostNotFoundException::new);

        if(!post.canDelete(command.personId())){
            throw new PostDomainException("The person is not the author of the post and cannot delete it");
        }

        postRepository.deleteById(post.getId());
    }

}
