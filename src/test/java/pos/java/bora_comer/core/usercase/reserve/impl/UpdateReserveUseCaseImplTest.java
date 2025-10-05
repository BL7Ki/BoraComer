package pos.java.bora_comer.core.usercase.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.gateway.reserve.ReserveUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefaultWithId;

class UpdateReserveUseCaseImplTest {

    private ReserveUpdateGateway reserveUpdateGateway;
    private UpdateReserveUseCaseImpl updateReserveUseCase;

    @BeforeEach
    void setUp() {
        reserveUpdateGateway = Mockito.mock(ReserveUpdateGateway.class);
        updateReserveUseCase = new UpdateReserveUseCaseImpl(reserveUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnReserve() {
        // Arrange
        Reserve reserveToUpdate = createDefaultWithId();

        when(reserveUpdateGateway.update(reserveToUpdate)).thenReturn(reserveToUpdate);

        // Act
        Reserve updatedReserve = updateReserveUseCase.execute(reserveToUpdate);

        // Assert
        assertThat(updatedReserve).isNotNull();
        verify(reserveUpdateGateway, times(1)).update(reserveToUpdate);
    }
}
