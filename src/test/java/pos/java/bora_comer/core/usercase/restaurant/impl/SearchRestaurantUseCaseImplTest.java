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
        Long id = 1L;
        Restaurant mockRestaurant = Restaurant.create(
                id,
                "Test Restaurant",
                "Test Address",
                "Italian",
                "9:00 - 22:00",
                10L
        );

        when(restaurantSearchGateway.findById(id)).thenReturn(mockRestaurant);

        // Act
        Restaurant result = searchRestaurantUseCase.findById(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Test Restaurant");
        verify(restaurantSearchGateway, times(1)).findById(id);
    }

    @Test
    void findAll_shouldReturnPageOfRestaurants() {
        // Arrange
        int page = 0;
        int size = 2;

        Restaurant r1 = Restaurant.create(
                1L,
                "Restaurant 1",
                "Address 1",
                "Japanese",
                "10:00 - 22:00",
                100L
        );
        Restaurant r2 = Restaurant.create(
                2L,
                "Restaurant 2",
                "Address 2",
                "Mexican",
                "11:00 - 23:00",
                101L
        );

        Page<Restaurant> mockPage = new PageImpl<>(List.of(r1, r2));
        when(restaurantSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<Restaurant> resultPage = searchRestaurantUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent()).extracting(Restaurant::getName)
                .containsExactly("Restaurant 1", "Restaurant 2");
        verify(restaurantSearchGateway, times(1)).findAll(page, size);
    }
}
