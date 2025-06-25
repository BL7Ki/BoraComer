package pos.java.bora_comer.infra.delivery.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserLoginUseCase userLoginUseCase;

    @InjectMocks
    private LoginController controller;

    @Test
    void deveriaRetornar200QuandoLoginForValido() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("messi10", "senha123");

        when(userLoginUseCase.execute("messi10", "senha123"))
                .thenReturn(LoginResponseEnum.SUCCESS);

        // Act
        ResponseEntity<String> response = controller.validateLogin(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoginResponseEnum.SUCCESS.getMessage(), response.getBody());

        verify(userLoginUseCase).execute("messi10", "senha123");
    }

    @Test
    void deveriaRetornar401QuandoLoginForInvalido() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("messi10", "senhaErrada");

        when(userLoginUseCase.execute("messi10", "senhaErrada"))
                .thenReturn(LoginResponseEnum.INVALID_PASSWORD);

        // Act
        ResponseEntity<String> response = controller.validateLogin(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(LoginResponseEnum.INVALID_PASSWORD.getMessage(), response.getBody());

        verify(userLoginUseCase).execute("messi10", "senhaErrada");
    }
}
