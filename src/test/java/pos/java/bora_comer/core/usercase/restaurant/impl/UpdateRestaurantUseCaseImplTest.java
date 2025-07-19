package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createDefaultWithId;

class UpdateRestaurantUseCaseImplTest {

    private RestaurantUpdateGateway restaurantUpdateGateway;
    private UpdateRestaurantUseCaseImpl updateRestaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantUpdateGateway = Mockito.mock(RestaurantUpdateGateway.class);
        updateRestaurantUseCase = new UpdateRestaurantUseCaseImpl(restaurantUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnRestaurant() {
        // Arrange
        Restaurant restaurantToUpdate = createDefaultWithId();

        when(restaurantUpdateGateway.update(restaurantToUpdate)).thenReturn(restaurantToUpdate);

        // Act
        Restaurant updatedRestaurant = updateRestaurantUseCase.execute(restaurantToUpdate);

        // Assert
        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getName()).isEqualTo("Restaurante Japa");
        verify(restaurantUpdateGateway, times(1)).update(restaurantToUpdate);
    }
}
