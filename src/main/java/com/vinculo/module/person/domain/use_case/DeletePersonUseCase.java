package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.command.DeletePersonCommand;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletePersonUseCase {

    private final PersonRepository personRepository;

    public DeletePersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void execute(DeletePersonCommand command){
        if(!personRepository.existsById(command.personId())){
            throw new PersonNotExistException();
        }

        personRepository.deleteById(command.personId());
    }


}

