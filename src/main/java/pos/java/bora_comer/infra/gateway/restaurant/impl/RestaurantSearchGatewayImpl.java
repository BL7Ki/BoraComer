package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantSearchGateway;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

@Component
public class RestaurantSearchGatewayImpl implements RestaurantSearchGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantSearchGatewayImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Restaurante com ID " + id + " não encontrado."));
    }

    @Override
    public Page<Restaurant> findAll(int page, int size, String cuisineType) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> resultPage;

        if (StringUtils.hasText(cuisineType)) {
            resultPage = restaurantRepository.findByCuisineType(cuisineType, pageable);
        } else {
            resultPage = restaurantRepository.findAll(pageable);
        }

        return resultPage.map(restaurantMapper::toDomain);
    }
}