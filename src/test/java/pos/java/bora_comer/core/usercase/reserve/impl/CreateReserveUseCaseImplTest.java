package pos.java.bora_comer.core.usercase.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.gateway.reserve.ReserveCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefaultWithId;

class CreateReserveUseCaseImplTest {

    private ReserveCreateGateway reserveCreateGateway;
    private CreateReserveUseCaseImpl createReserveUseCase;

    @BeforeEach
    void setUp() {
        reserveCreateGateway = Mockito.mock(ReserveCreateGateway.class);
        createReserveUseCase = new CreateReserveUseCaseImpl(reserveCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedReserve() {
        // Arrange
        Reserve reserveToSave = createDefault();

        Reserve savedReserve = createDefaultWithId();

        when(reserveCreateGateway.save(any(Reserve.class))).thenReturn(savedReserve);

        // Act
        Reserve result = createReserveUseCase.execute(reserveToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(reserveToSave.getUserId(), result.getUserId());
        assertEquals(reserveToSave.getRestaurantId(), result.getRestaurantId());
        assertEquals(reserveToSave.getQuantity(), result.getQuantity());
        assertEquals(reserveToSave.getDateTimeReserve(), result.getDateTimeReserve());
        verify(reserveCreateGateway, times(1)).save(reserveToSave);
    }
}
