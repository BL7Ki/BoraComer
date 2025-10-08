package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantSearchGatewayImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private RestaurantSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantMapper = mock(RestaurantMapper.class);
        gateway = new RestaurantSearchGatewayImpl(restaurantRepository, restaurantMapper);
    }

    @Test
    @DisplayName("findById should return Restaurant when found")
    void findById_shouldReturnRestaurant_whenFound() {
        Long id = 1L;
        RestaurantEntityMock entity = new RestaurantEntityMock(id);
        Restaurant domain = mock(Restaurant.class);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restaurantMapper.toDomain(entity)).thenReturn(domain);

        Restaurant result = gateway.findById(id);

        assertSame(domain, result);
        verify(restaurantRepository).findById(id);
        verify(restaurantMapper).toDomain(entity);
    }

    @Test
    @DisplayName("findById should throw SummerNotFoundException when not found")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Restaurante com ID " + id + " não encontrado.", ex.getMessage());

        verify(restaurantRepository).findById(id);
        verifyNoInteractions(restaurantMapper);
    }

    @Test
    @DisplayName("findAll should return paged Restaurants when cuisineType is null")
    void findAll_shouldReturnPagedRestaurants_whenCuisineTypeIsNull() {
        int page = 0;
        int size = 2;

        var entity1 = mock(pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity.class);
        var entity2 = mock(pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity> entityPage =
                new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        Restaurant domain1 = mock(Restaurant.class);
        Restaurant domain2 = mock(Restaurant.class);

        when(restaurantRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(restaurantMapper.toDomain(entity1)).thenReturn(domain1);
        when(restaurantMapper.toDomain(entity2)).thenReturn(domain2);

        Page<Restaurant> resultPage = gateway.findAll(page, size, null);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(restaurantRepository).findAll(pageRequest);
        verify(restaurantMapper).toDomain(entity1);
        verify(restaurantMapper).toDomain(entity2);
    }

    @Test
    @DisplayName("findAll should call findByCuisineType when cuisineType is provided")
    void findAll_shouldFilterByCuisineType_whenProvided() {
        int page = 0;
        int size = 2;
        String cuisineType = "Japonesa";

        var entity = mock(pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity.class);
        var domain = mock(Restaurant.class);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity> entityPage =
                new PageImpl<>(List.of(entity), pageRequest, 1);

        when(restaurantRepository.findByCuisineType(cuisineType, pageRequest)).thenReturn(entityPage);
        when(restaurantMapper.toDomain(entity)).thenReturn(domain);

        Page<Restaurant> resultPage = gateway.findAll(page, size, cuisineType);

        assertEquals(1, resultPage.getContent().size());
        assertEquals(domain, resultPage.getContent().get(0));

        verify(restaurantRepository).findByCuisineType(cuisineType, pageRequest);
        verify(restaurantMapper).toDomain(entity);
    }

    // Dummy RestaurantEntity mock class
    private static class RestaurantEntityMock extends pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity {
        private final Long id;
        public RestaurantEntityMock(Long id) { this.id = id; }
        @Override public Long getId() { return id; }
    }
}
