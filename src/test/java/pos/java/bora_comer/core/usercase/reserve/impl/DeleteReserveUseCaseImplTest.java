package pos.java.bora_comer.core.usercase.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.reserve.ReserveDeleteGateway;

import static org.mockito.Mockito.*;

class DeleteReserveUseCaseImplTest {

    private ReserveDeleteGateway reserveDeleteGateway;
    private DeleteReserveUseCaseImpl deleteReserveUseCase;

    @BeforeEach
    void setUp() {
        reserveDeleteGateway = Mockito.mock(ReserveDeleteGateway.class);
        deleteReserveUseCase = new DeleteReserveUseCaseImpl(reserveDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long reserveId = 5L;

        // Act
        deleteReserveUseCase.execute(reserveId);

        // Assert
        verify(reserveDeleteGateway, times(1)).deleteById(reserveId);
    }
}
