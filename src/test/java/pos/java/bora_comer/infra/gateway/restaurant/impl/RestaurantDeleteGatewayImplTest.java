package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantDeleteGatewayImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        gateway = new RestaurantDeleteGatewayImpl(restaurantRepository);
    }

    @Test
    @DisplayName("Should delete restaurant when found by id")
    void shouldDeleteRestaurantWhenFound() {
        Long restaurantId = 1L;
        RestaurantEntity entity = mock(RestaurantEntity.class);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(restaurantId));

        // Verify repository delete was called
        verify(restaurantRepository, times(1)).delete(entity);
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    @DisplayName("Should throw SummerNotFoundException when restaurant not found")
    void shouldThrowExceptionWhenNotFound() {
        Long restaurantId = 2L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(restaurantId));
        assertEquals("Restaurante com ID " + restaurantId + " não encontrado.", ex.getMessage());

        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, never()).delete(any());
    }
}
