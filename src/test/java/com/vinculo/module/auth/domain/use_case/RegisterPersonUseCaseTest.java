package com.vinculo.module.auth.domain.use_case;

import com.vinculo.module.auth.domain.command.RegisterPersonCommand;
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
class RegisterPersonUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @InjectMocks
    private RegisterPersonUseCase registerPersonUseCase;

    @Test
    @DisplayName("Should successfully register a new person")
    void shouldRegisterNewPerson() {
        // Arrange
        RegisterPersonCommand command = new RegisterPersonCommand(
            "John Doe",
            "john@example.com",
            "+1234567890",
            "password123"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(false);
        when(phoneNumberValidator.isValid(command.phoneNumber())).thenReturn(true);
        when(passwordEncoder.encode(command.password())).thenReturn("hashed-password");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        // Act
        registerPersonUseCase.execute(command);

        // Assert
        verify(personRepository).existsByEmail(command.email());
        verify(phoneNumberValidator).isValid(command.phoneNumber());
        verify(passwordEncoder).encode(command.password());
        verify(personRepository).save(personCaptor.capture());

        Person savedPerson = personCaptor.getValue();
        assertEquals("John Doe", savedPerson.getName());
        assertEquals("john@example.com", savedPerson.getEmail());
        assertEquals("+1234567890", savedPerson.getPhoneNumber());
        assertEquals("hashed-password", savedPerson.getPassword());
    }

    @Test
    @DisplayName("Should throw EmailAlreadyInUseException when email exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        RegisterPersonCommand command = new RegisterPersonCommand(
            "Jane Doe",
            "existing@example.com",
            "+9876543210",
            "password456"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyInUseException.class, 
            () -> registerPersonUseCase.execute(command));
        
        verify(personRepository).existsByEmail(command.email());
        verify(phoneNumberValidator, never()).isValid(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    @DisplayName("Should throw PhoneNumberIsNotValidException when phone number is invalid")
    void shouldThrowExceptionWhenPhoneNumberInvalid() {
        // Arrange
        RegisterPersonCommand command = new RegisterPersonCommand(
            "Bob Smith",
            "bob@example.com",
            "invalid-phone",
            "password789"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(false);
        when(phoneNumberValidator.isValid(command.phoneNumber())).thenReturn(false);

        // Act & Assert
        assertThrows(PhoneNumberIsNotValidException.class, 
            () -> registerPersonUseCase.execute(command));
        
        verify(personRepository).existsByEmail(command.email());
        verify(phoneNumberValidator).isValid(command.phoneNumber());
        verify(passwordEncoder, never()).encode(anyString());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    @DisplayName("Should validate email before phone number")
    void shouldValidateEmailBeforePhoneNumber() {
        // Arrange
        RegisterPersonCommand command = new RegisterPersonCommand(
            "Alice Johnson",
            "existing@example.com",
            "invalid-phone",
            "password000"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyInUseException.class, 
            () -> registerPersonUseCase.execute(command));
        
        verify(personRepository).existsByEmail(command.email());
        verify(phoneNumberValidator, never()).isValid(anyString());
    }

    @Test
    @DisplayName("Should encode password before saving")
    void shouldEncodePasswordBeforeSaving() {
        // Arrange
        RegisterPersonCommand command = new RegisterPersonCommand(
            "Charlie Brown",
            "charlie@example.com",
            "+1122334455",
            "plain-password"
        );

        when(personRepository.existsByEmail(command.email())).thenReturn(false);
        when(phoneNumberValidator.isValid(command.phoneNumber())).thenReturn(true);
        when(passwordEncoder.encode("plain-password")).thenReturn("encoded-password");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        // Act
        registerPersonUseCase.execute(command);

        // Assert
        verify(personRepository).save(personCaptor.capture());
        Person savedPerson = personCaptor.getValue();
        assertEquals("encoded-password", savedPerson.getPassword());
        assertNotEquals("plain-password", savedPerson.getPassword());
    }
}
