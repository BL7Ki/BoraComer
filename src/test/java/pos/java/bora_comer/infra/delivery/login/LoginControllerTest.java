package pos.java.bora_comer.infra.delivery.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.login.dto.LoginResponseDTO;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserLoginUseCase userLoginUseCase;

    @InjectMocks
    private LoginController controller;

    @Test
    void deveriaRetornarTokenQuandoLoginForValido() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("messi10", "senha123");
        String fakeToken = "fake-jwt-token";

        when(userLoginUseCase.execute("messi10", "senha123")).thenReturn(fakeToken);

        // Act
        ResponseEntity<LoginResponseDTO> response = controller.validateLogin(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fakeToken, response.getBody().token());
        assertEquals("Bearer", response.getBody().type());

        verify(userLoginUseCase).execute("messi10", "senha123");
    }
}
