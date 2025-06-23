package pos.java.bora_comer.infra.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.user.DeleteUserController;

@ExtendWith(MockitoExtension.class)
class DeleteUserControllerTest {

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private DeleteUserController controller;

    @Test
    void deveriaDeletarUsuarioERetornar204() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<Void> response = controller.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o use case foi chamado corretamente
        verify(deleteUserUseCase).execute(userId);
    }
}
