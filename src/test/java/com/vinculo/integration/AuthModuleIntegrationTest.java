package com.vinculo.integration;

import com.vinculo.module.auth.domain.command.LoginCommand;
import com.vinculo.module.auth.domain.command.RegisterPersonCommand;
import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.auth.domain.port.AuthenticatorPort;
import com.vinculo.module.auth.domain.port.TokenProvider;
import com.vinculo.module.auth.domain.use_case.LoginUseCase;
import com.vinculo.module.auth.domain.use_case.RegisterPersonUseCase;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.model.RoleUser;
import com.vinculo.module.person.domain.port.PasswordEncoder;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.person.domain.port.PhoneNumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Integration tests for the Authentication module.
 * Tests the integration between use cases, ports, and domain logic.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Authentication Module Integration Tests")
class AuthModuleIntegrationTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @Mock
    private AuthenticatorPort authenticatorPort;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("Should successfully register a new person end-to-end")
    void shouldRegisterPersonEndToEnd() {
        // Arrange
        RegisterPersonUseCase registerUseCase = new RegisterPersonUseCase(
                personRepository,
                passwordEncoder,
                phoneNumberValidator
        );

        RegisterPersonCommand command = new RegisterPersonCommand(
                "John Doe",
                "john@example.com",
                "+5511999999999",
                "password123"
        );

        when(personRepository.existsByEmail(anyString())).thenReturn(false);
        when(phoneNumberValidator.isValid(anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        registerUseCase.execute(command);

        // Assert
        verify(personRepository).existsByEmail("john@example.com");
        verify(phoneNumberValidator).isValid("+5511999999999");
        verify(passwordEncoder).encode("password123");
        verify(personRepository).save(any(Person.class));
    }

    @Test
    @DisplayName("Should successfully login with valid credentials end-to-end")
    void shouldLoginWithValidCredentials() {
        // Arrange
        LoginUseCase loginUseCase = new LoginUseCase(
                authenticatorPort,
                tokenProvider
        );

        LoginCommand command = new LoginCommand(
                "user@example.com",
                "password123"
        );

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                "user-123",
                "user@example.com",
                List.of(RoleUser.NORMAL.name())
        );

        when(authenticatorPort.authenticate(anyString(), anyString()))
                .thenReturn(authenticatedUser);
        when(tokenProvider.createToken(anyString(), anyString(), anyList()))
                .thenReturn("jwt-token");

        // Act
        String token = loginUseCase.execute(command);

        // Assert
        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(authenticatorPort).authenticate("user@example.com", "password123");
        verify(tokenProvider).createToken("user@example.com", "user-123", List.of(RoleUser.NORMAL.name()));
    }

    @Test
    @DisplayName("Should prevent duplicate email registration")
    void shouldPreventDuplicateEmailRegistration() {
        // Arrange
        RegisterPersonUseCase registerUseCase = new RegisterPersonUseCase(
                personRepository,
                passwordEncoder,
                phoneNumberValidator
        );

        RegisterPersonCommand command = new RegisterPersonCommand(
                "Jane Doe",
                "existing@example.com",
                "+5511988888888",
                "password456"
        );

        when(personRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(Exception.class, () -> registerUseCase.execute(command));
        verify(personRepository).existsByEmail("existing@example.com");
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    @DisplayName("Should validate phone number during registration")
    void shouldValidatePhoneNumberDuringRegistration() {
        // Arrange
        RegisterPersonUseCase registerUseCase = new RegisterPersonUseCase(
                personRepository,
                passwordEncoder,
                phoneNumberValidator
        );

        RegisterPersonCommand command = new RegisterPersonCommand(
                "Bob Smith",
                "bob@example.com",
                "invalid-phone",
                "password789"
        );

        when(personRepository.existsByEmail(anyString())).thenReturn(false);
        when(phoneNumberValidator.isValid("invalid-phone")).thenReturn(false);

        // Act & Assert
        assertThrows(Exception.class, () -> registerUseCase.execute(command));
        verify(phoneNumberValidator).isValid("invalid-phone");
        verify(personRepository, never()).save(any(Person.class));
    }
}
