package com.vinculo.module.auth.domain.use_case;

import com.vinculo.module.auth.domain.command.LoginCommand;
import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.auth.domain.port.AuthenticatorPort;
import com.vinculo.module.auth.domain.port.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private AuthenticatorPort authenticatorPort;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    @DisplayName("Should successfully login and return token")
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "user@example.com";
        String password = "password123";
        LoginCommand command = new LoginCommand(email, password);
        
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
            "user-id-123", 
            email, 
            List.of("ROLE_USER")
        );
        String expectedToken = "jwt-token-xyz";

        when(authenticatorPort.authenticate(email, password))
            .thenReturn(authenticatedUser);
        when(tokenProvider.createToken(email, "user-id-123", List.of("ROLE_USER")))
            .thenReturn(expectedToken);

        // Act
        String actualToken = loginUseCase.execute(command);

        // Assert
        assertNotNull(actualToken);
        assertEquals(expectedToken, actualToken);
        verify(authenticatorPort).authenticate(email, password);
        verify(tokenProvider).createToken(email, "user-id-123", List.of("ROLE_USER"));
    }

    @Test
    @DisplayName("Should handle authentication with multiple roles")
    void shouldHandleMultipleRoles() {
        // Arrange
        String email = "admin@example.com";
        String password = "admin123";
        LoginCommand command = new LoginCommand(email, password);
        
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
            "admin-id-456", 
            email, 
            roles
        );
        String expectedToken = "admin-jwt-token";

        when(authenticatorPort.authenticate(email, password))
            .thenReturn(authenticatedUser);
        when(tokenProvider.createToken(email, "admin-id-456", roles))
            .thenReturn(expectedToken);

        // Act
        String actualToken = loginUseCase.execute(command);

        // Assert
        assertEquals(expectedToken, actualToken);
        verify(tokenProvider).createToken(email, "admin-id-456", roles);
    }

    @Test
    @DisplayName("Should propagate authentication exception when credentials are invalid")
    void shouldPropagateAuthenticationException() {
        // Arrange
        String email = "user@example.com";
        String password = "wrong-password";
        LoginCommand command = new LoginCommand(email, password);

        when(authenticatorPort.authenticate(email, password))
            .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> loginUseCase.execute(command));
        verify(authenticatorPort).authenticate(email, password);
        verify(tokenProvider, never()).createToken(anyString(), anyString(), anyList());
    }
}
