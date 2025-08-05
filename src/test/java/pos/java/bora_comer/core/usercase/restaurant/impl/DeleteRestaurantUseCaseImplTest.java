package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantDeleteGateway;

import static org.mockito.Mockito.*;

class DeleteRestaurantUseCaseImplTest {

    private RestaurantDeleteGateway restaurantDeleteGateway;
    private DeleteRestaurantUseCaseImpl deleteRestaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantDeleteGateway = Mockito.mock(RestaurantDeleteGateway.class);
        deleteRestaurantUseCase = new DeleteRestaurantUseCaseImpl(restaurantDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long restaurantId = 5L;

        // Act
        deleteRestaurantUseCase.execute(restaurantId);

        // Assert
        verify(restaurantDeleteGateway, times(1)).deleteById(restaurantId);
    }
}
