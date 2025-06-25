package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.user.UserDeleteGateway;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteUserUseCaseImplTest {

    private UserDeleteGateway userDeleteGateway;
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @BeforeEach
    void setUp() {
        userDeleteGateway = mock(UserDeleteGateway.class);
        deleteUserUseCase = new DeleteUserUseCaseImpl(userDeleteGateway);
    }

    @Test
    void deveChamarDeleteByIdQuandoExecutar() throws SummerNotFoundException {
        Long userId = 1L;

        // Chamada do método
        deleteUserUseCase.execute(userId);

        // Verifica se o método deleteById foi chamado com o id correto
        verify(userDeleteGateway, times(1)).deleteById(userId);
    }

    @Test
    void deveLancarSummerNotFoundExceptionQuandoGatewayLancar() throws SummerNotFoundException {
        Long userId = 2L;

        // Simula exceção lançada pelo gateway
        doThrow(new SummerNotFoundException("Usuário não encontrado"))
                .when(userDeleteGateway).deleteById(userId);

        // Verifica se a exceção é propagada
        assertThrows(SummerNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });

        verify(userDeleteGateway, times(1)).deleteById(userId);
    }
}
