package com.vinculo.module.post.application.handler;

import com.vinculo.module.post.domain.command.DeletePostCommand;
import com.vinculo.module.post.domain.use_case.DeletePostUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePostHandler {

    private final DeletePostUseCase deletePostUseCase;

    public DeletePostHandler(DeletePostUseCase deletePostUseCase) {
        this.deletePostUseCase = deletePostUseCase;
    }

    @Transactional
    public void handle(String postId, String personId){
        var command = DeletePostCommand.of(postId, personId);

        deletePostUseCase.execute(command);
    }

}
