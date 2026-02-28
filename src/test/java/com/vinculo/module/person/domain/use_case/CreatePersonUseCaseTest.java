package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.command.CreatePersonCommand;
import com.vinculo.module.person.domain.exception.email.EmailAlreadyInUseException;
import com.vinculo.module.person.domain.exception.number.PhoneNumberIsNotValidException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PasswordEncoder;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.person.domain.port.PhoneNumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePersonUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @InjectMocks
    private CreatePersonUseCase createPersonUseCase;

    @Test
    @DisplayName("Should successfully create a new person")
    void shouldCreatePersonSuccessfully() {
        // Arrange
        CreatePersonCommand command = new CreatePersonCommand(
            "John Doe",
            "johndoe",
            "+1234567890",
            "john@example.com",
            "password123"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(false);
        when(phoneNumberValidator.isValid(command.phoneNumber())).thenReturn(true);
        when(passwordEncoder.encode(command.password())).thenReturn("encoded-password");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        // Act
        createPersonUseCase.execute(command);

        // Assert
        verify(personRepository).save(personCaptor.capture());
        Person savedPerson = personCaptor.getValue();
        
        assertEquals("John Doe", savedPerson.getName());
        assertEquals("johndoe", savedPerson.getUsername());
        assertEquals("john@example.com", savedPerson.getEmail());
        assertEquals("+1234567890", savedPerson.getPhoneNumber());
        assertEquals("encoded-password", savedPerson.getPassword());
    }

    @Test
    @DisplayName("Should throw EmailAlreadyInUseException when email exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        CreatePersonCommand command = new CreatePersonCommand(
            "Jane Doe",
            "janedoe",
            "+9876543210",
            "existing@example.com",
            "password456"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyInUseException.class, 
            () -> createPersonUseCase.execute(command));
        
        verify(personRepository).existsByEmail(command.email());
        verify(phoneNumberValidator, never()).isValid(anyString());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    @DisplayName("Should throw PhoneNumberIsNotValidException when phone is invalid")
    void shouldThrowExceptionWhenPhoneInvalid() {
        // Arrange
        CreatePersonCommand command = new CreatePersonCommand(
            "Bob Smith",
            "bobsmith",
            "invalid",
            "bob@example.com",
            "password789"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(false);
        when(phoneNumberValidator.isValid(command.phoneNumber())).thenReturn(false);

        // Act & Assert
        assertThrows(PhoneNumberIsNotValidException.class, 
            () -> createPersonUseCase.execute(command));
        
        verify(phoneNumberValidator).isValid(command.phoneNumber());
        verify(personRepository, never()).save(any(Person.class));
    }
}
