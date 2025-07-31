package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createDefault;

class RestaurantCreateGatewayImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private RestaurantCreateGatewayImpl gateway;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantMapper = mock(RestaurantMapper.class);
        userRepository = mock(UserRepository.class);
        gateway = new RestaurantCreateGatewayImpl(restaurantRepository, restaurantMapper, userRepository);
    }

    @Test
    @DisplayName("Should throw exception if restaurant name already exists")
    void shouldThrowExceptionIfNameExists() {
        Restaurant restaurant = createDefault();

        when(restaurantRepository.existsByName(restaurant.getName())).thenReturn(true);

        RestaurantDomainException ex = assertThrows(RestaurantDomainException.class, () -> gateway.save(restaurant));
        assertEquals("Já existe um restaurante com esse nome.", ex.getMessage());

        verify(restaurantRepository, times(1)).existsByName(restaurant.getName());
        verifyNoMoreInteractions(restaurantRepository);
        verifyNoInteractions(restaurantMapper);
    }

    @Test
    @DisplayName("Should throw exception if owner does not exist")
    void shouldThrowExceptionIfOwnerDoesNotExist() {
        Restaurant restaurant = createDefault();

        when(restaurantRepository.existsByName(restaurant.getName())).thenReturn(false);
        when(userRepository.existsById(restaurant.getOwnerId())).thenReturn(false);

        RestaurantDomainException ex = assertThrows(RestaurantDomainException.class, () -> gateway.save(restaurant));
        assertEquals("Este dono não existe. Tente criá-lo!", ex.getMessage());

        verify(restaurantRepository, times(1)).existsByName(restaurant.getName());
        verify(userRepository, times(1)).existsById(restaurant.getOwnerId());
        verifyNoInteractions(restaurantMapper);
    }

    @Test
    @DisplayName("Should save and return restaurant successfully")
    void shouldSaveAndReturnRestaurant() {
        Restaurant restaurant = createDefault();
        RestaurantEntity entityToSave = new RestaurantEntity();
        RestaurantEntity savedEntity = new RestaurantEntity();
        Restaurant domainFromSaved = createDefault();

        when(restaurantRepository.existsByName(restaurant.getName())).thenReturn(false);
        when(userRepository.existsById(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantMapper.toEntity(restaurant)).thenReturn(entityToSave);
        when(restaurantRepository.save(entityToSave)).thenReturn(savedEntity);
        when(restaurantMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        Restaurant result = gateway.save(restaurant);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getName(), result.getName());

        verify(restaurantRepository, times(1)).existsByName(restaurant.getName());
        verify(userRepository, times(1)).existsById(restaurant.getOwnerId());
        verify(restaurantMapper, times(1)).toEntity(restaurant);
        verify(restaurantRepository, times(1)).save(entityToSave);
        verify(restaurantMapper, times(1)).toDomain(savedEntity);
    }
}
