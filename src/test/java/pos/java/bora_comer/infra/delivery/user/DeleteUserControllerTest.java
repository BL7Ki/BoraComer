package pos.java.bora_comer.infra.delivery.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DeleteUserControllerTest {

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private DeleteUserController controller;

    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void deveriaDeletarUsuarioERetornar204() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<Void> response = controller.deleteUser(autorizationHeader, userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o use case foi chamado corretamente
        verify(deleteUserUseCase).execute(userId);
    }
}
