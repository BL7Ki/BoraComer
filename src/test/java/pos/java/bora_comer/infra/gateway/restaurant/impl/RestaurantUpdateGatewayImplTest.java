package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUpdateGatewayImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private UserRepository userRepository;
    private RestaurantUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantMapper = mock(RestaurantMapper.class);
        userRepository = mock(UserRepository.class);

        gateway = new RestaurantUpdateGatewayImpl(restaurantRepository, restaurantMapper, userRepository);
    }

    @Test
    @DisplayName("Should update restaurant successfully when ownerId not changed")
    void shouldUpdateRestaurantWhenOwnerIdNotChanged() {
        Long id = 1L;
        Long ownerId = 10L;
        Restaurant domainRestaurant = Restaurant.create(id, "NewName", "NewAddress", "NewCuisine", "9am-9pm", ownerId);

        RestaurantEntity entity = mock(RestaurantEntity.class);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOwnerId()).thenReturn(ownerId);

        RestaurantEntity updatedEntity = mock(RestaurantEntity.class);
        when(restaurantRepository.save(entity)).thenReturn(updatedEntity);

        Restaurant expectedDomain = mock(Restaurant.class);
        when(restaurantMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Restaurant result = gateway.update(domainRestaurant);

        verify(entity).updateName("NewName");
        verify(entity).updateAddress("NewAddress");
        verify(entity).updateCuisineType("NewCuisine");
        verify(entity).updateOpeningHours("9am-9pm");
        verify(entity).updateLastModifiedDate();

        verify(restaurantRepository).save(entity);
        verify(restaurantMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Should update restaurant and change owner when ownerId changed")
    void shouldUpdateRestaurantAndChangeOwner() {
        Long id = 1L;
        Long oldOwnerId = 10L;
        Long newOwnerId = 20L;

        Restaurant domainRestaurant = Restaurant.create(id, "NewName", "NewAddress", "NewCuisine", "9am-9pm", newOwnerId);

        RestaurantEntity entity = mock(RestaurantEntity.class);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOwnerId()).thenReturn(oldOwnerId);

        when(userRepository.findById(newOwnerId)).thenReturn(Optional.of(mock()));

        RestaurantEntity updatedEntity = mock(RestaurantEntity.class);
        when(restaurantRepository.save(entity)).thenReturn(updatedEntity);

        Restaurant expectedDomain = mock(Restaurant.class);
        when(restaurantMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Restaurant result = gateway.update(domainRestaurant);

        verify(userRepository).findById(newOwnerId);
        verify(entity).updateOwnerId(newOwnerId);

        verify(entity).updateName("NewName");
        verify(entity).updateAddress("NewAddress");
        verify(entity).updateCuisineType("NewCuisine");
        verify(entity).updateOpeningHours("9am-9pm");
        verify(entity).updateLastModifiedDate();

        verify(restaurantRepository).save(entity);
        verify(restaurantMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Should throw exception when restaurant not found")
    void shouldThrowWhenRestaurantNotFound() {
        Long id = 1L;
        Restaurant domainRestaurant = Restaurant.create(id, "Name", "Address", "Cuisine", "Hours", 10L);

        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        RestaurantDomainException ex = assertThrows(RestaurantDomainException.class, () -> gateway.update(domainRestaurant));
        assertEquals("Restaurante com ID " + id + " não encontrado.", ex.getMessage());

        verify(restaurantRepository).findById(id);
        verifyNoMoreInteractions(restaurantRepository, userRepository, restaurantMapper);
    }

    @Test
    @DisplayName("Should throw exception when new owner not found")
    void shouldThrowWhenNewOwnerNotFound() {
        Long id = 1L;
        Long oldOwnerId = 10L;
        Long newOwnerId = 20L;
        Restaurant domainRestaurant = Restaurant.create(id, "Name", "Address", "Cuisine", "Hours", newOwnerId);

        RestaurantEntity entity = mock(RestaurantEntity.class);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOwnerId()).thenReturn(oldOwnerId);

        when(userRepository.findById(newOwnerId)).thenReturn(Optional.empty());

        RestaurantDomainException ex = assertThrows(RestaurantDomainException.class, () -> gateway.update(domainRestaurant));
        assertEquals("Usuário dono com ID " + newOwnerId + " não encontrado.", ex.getMessage());

        verify(restaurantRepository).findById(id);
        verify(userRepository).findById(newOwnerId);

        verifyNoMoreInteractions(restaurantRepository, userRepository, restaurantMapper);
    }
}
