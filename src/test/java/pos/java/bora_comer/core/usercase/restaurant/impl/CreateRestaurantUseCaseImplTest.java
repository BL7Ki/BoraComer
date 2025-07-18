package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseImplTest {

    private RestaurantCreateGateway restaurantCreateGateway;
    private CreateRestaurantUseCaseImpl createRestaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantCreateGateway = Mockito.mock(RestaurantCreateGateway.class);
        createRestaurantUseCase = new CreateRestaurantUseCaseImpl(restaurantCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedRestaurant() {
        // Arrange
        Restaurant restaurantToSave = Restaurant.create(
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        Restaurant savedRestaurant = Restaurant.create(
                10L,
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        when(restaurantCreateGateway.save(any(Restaurant.class))).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.execute(restaurantToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Sushi Place", result.getName());
        verify(restaurantCreateGateway, times(1)).save(restaurantToSave);
    }
}
