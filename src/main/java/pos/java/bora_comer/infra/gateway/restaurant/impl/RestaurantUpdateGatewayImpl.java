package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantUpdateGateway;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

@Component
public class RestaurantUpdateGatewayImpl implements RestaurantUpdateGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserRepository userRepository;

    public RestaurantUpdateGatewayImpl(RestaurantRepository restaurantRepository,
                                       RestaurantMapper restaurantMapper,
                                       UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Restaurant update(Restaurant restaurant) throws RestaurantDomainException {
        RestaurantEntity entity = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new RestaurantDomainException("Restaurante com ID " + restaurant.getId() + " não encontrado."));

        if (!restaurant.getOwnerId().equals(entity.getOwnerId())) {
            userRepository.findById(restaurant.getOwnerId())
                    .orElseThrow(() -> new RestaurantDomainException("Usuário dono com ID " + restaurant.getOwnerId() + " não encontrado."));
            entity.updateOwnerId(restaurant.getOwnerId());
        }

        entity.updateName(restaurant.getName());
        entity.updateAddress(restaurant.getAddress());
        entity.updateCuisineType(restaurant.getCuisineType());
        entity.updateOpeningHours(restaurant.getOpeningHours());
        entity.updateLastModifiedDate();

        RestaurantEntity updatedEntity = restaurantRepository.save(entity);
        return restaurantMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDomain);
    }
}
