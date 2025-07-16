package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantCreateGateway;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;

@Component
public class RestaurantCreateGatewayImpl implements RestaurantCreateGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantCreateGatewayImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantRepository.existsByName(name);
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        if (existsByName(restaurant.getName())) {
            throw new RestaurantDomainException("Já existe um restaurante com esse nome.");
        }

        var restaurantEntity = restaurantMapper.toEntity(restaurant);
        var savedEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.toDomain(savedEntity);
    }
}
