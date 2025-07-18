package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        Restaurant restaurantToUpdate = Restaurant.create(
                1L,
                "Updated Name",
                "Updated Address",
                "Updated Cuisine",
                "10:00 - 23:00",
                5L
        );

        when(restaurantUpdateGateway.update(restaurantToUpdate)).thenReturn(restaurantToUpdate);

        // Act
        Restaurant updatedRestaurant = updateRestaurantUseCase.execute(restaurantToUpdate);

        // Assert
        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getName()).isEqualTo("Updated Name");
        verify(restaurantUpdateGateway, times(1)).update(restaurantToUpdate);
    }
}
