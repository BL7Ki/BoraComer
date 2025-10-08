package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createDefaultWithId;

class SearchRestaurantUseCaseImplTest {

    private RestaurantSearchGateway restaurantSearchGateway;
    private SearchRestaurantUseCaseImpl searchRestaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantSearchGateway = Mockito.mock(RestaurantSearchGateway.class);
        searchRestaurantUseCase = new SearchRestaurantUseCaseImpl(restaurantSearchGateway);
    }

    @Test
    void findById_shouldReturnRestaurant() {
        // Arrange
        Restaurant mockRestaurant = createDefaultWithId();

        when(restaurantSearchGateway.findById(10L)).thenReturn(mockRestaurant);

        // Act
        Restaurant result = searchRestaurantUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Restaurante Japa");
        verify(restaurantSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfRestaurants() {
        // Arrange
        int page = 0;
        int size = 2;

        Restaurant r1 = createDefaultWithId();
        Restaurant r2 = createDefaultWithId();

        Page<Restaurant> mockPage = new PageImpl<>(List.of(r1, r2));
        when(restaurantSearchGateway.findAll(page, size, null)).thenReturn(mockPage);

        // Act
        Page<Restaurant> resultPage = searchRestaurantUseCase.findAll(page, size, null);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent()).extracting(Restaurant::getName)
                .containsExactly("Restaurante Japa", "Restaurante Japa");
        verify(restaurantSearchGateway, times(1)).findAll(page, size, null);
    }
}
