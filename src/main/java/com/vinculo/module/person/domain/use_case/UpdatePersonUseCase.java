package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.command.UpdatePersonCommand;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.exception.number.PhoneNumberIsNotValidException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.person.domain.port.PhoneNumberValidator;
import org.springframework.stereotype.Component;

@Component
public class UpdatePersonUseCase {

    private final PersonRepository personRepository;
    private final PhoneNumberValidator phoneNumberValidator;

    public UpdatePersonUseCase(PersonRepository personRepository, PhoneNumberValidator phoneNumberValidator) {
        this.personRepository = personRepository;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public void execute(UpdatePersonCommand command) {
        Person person = personRepository.findById(command.personId())
                .orElseThrow(PersonNotExistException::new);

        if (command.phoneNumber() != null && !phoneNumberValidator.isValid(command.phoneNumber())) {
            throw new PhoneNumberIsNotValidException();
        }

        person.update(
                command.name(),
                command.phoneNumber()
        );

        personRepository.save(person);
    }

}