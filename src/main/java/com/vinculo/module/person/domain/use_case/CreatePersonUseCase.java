package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.command.CreatePersonCommand;
import com.vinculo.module.person.domain.exception.email.EmailAlreadyInUseException;
import com.vinculo.module.person.domain.exception.number.PhoneNumberIsNotValidException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PasswordEncoder;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.person.domain.port.PhoneNumberValidator;
import org.springframework.stereotype.Component;

@Component
public class CreatePersonUseCase {

    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;
    private final PhoneNumberValidator phoneNumberValidator;

    public CreatePersonUseCase(PersonRepository personRepository, PasswordEncoder passwordEncoder, PhoneNumberValidator phoneNumberValidator) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public void execute(CreatePersonCommand command) {
        if (personRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyInUseException();
        }
        
        if(!phoneNumberValidator.isValid(command.phoneNumber())){
            throw new PhoneNumberIsNotValidException();
        }
        
        String passwordHash = passwordEncoder.encode(command.password());

        Person person = Person.builder()
                .name(command.name())
                .email(command.email())
                .phoneNumber(command.phoneNumber())
                .password(passwordHash)
                .build();

        personRepository.save(person);
    }


}
