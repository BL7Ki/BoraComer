package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantCreateGateway;
import pos.java.bora_comer.core.usercase.restaurant.CreateRestaurantUseCase;

@Service
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {

    private final RestaurantCreateGateway restaurantCreateGateway;

    public CreateRestaurantUseCaseImpl(RestaurantCreateGateway restaurantCreateGateway) {
        this.restaurantCreateGateway = restaurantCreateGateway;
    }

    @Override
    public Restaurant execute(Restaurant restaurant) {
        return restaurantCreateGateway.save(restaurant);
    }
}
