package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantSearchGateway;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;

@Service
public class SearchRestaurantUseCaseImpl implements SearchRestaurantUseCase {

    private final RestaurantSearchGateway restaurantSearchGateway;

    public SearchRestaurantUseCaseImpl(RestaurantSearchGateway restaurantSearchGateway) {
        this.restaurantSearchGateway = restaurantSearchGateway;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantSearchGateway.findById(id);
    }

    @Override
    public Page<Restaurant> findAll(int page, int size, String cuisineType) {
        return restaurantSearchGateway.findAll(page, size, cuisineType);
    }
}