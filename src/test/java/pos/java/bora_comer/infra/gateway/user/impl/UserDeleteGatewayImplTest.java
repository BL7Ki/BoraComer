package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDeleteGatewayImplTest {

    private UserRepository userRepository;
    private UserDeleteGatewayImpl userDeleteGateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDeleteGateway = new UserDeleteGatewayImpl(userRepository);
    }

    @Test
    void deveDeletarUsuarioQuandoIdExistir() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> userDeleteGateway.deleteById(id));

        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        Long id = 2L;

        when(userRepository.existsById(id)).thenReturn(false);

        SummerNotFoundException exception = assertThrows(SummerNotFoundException.class,
                () -> userDeleteGateway.deleteById(id));

        assertEquals("User with ID 2 not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
