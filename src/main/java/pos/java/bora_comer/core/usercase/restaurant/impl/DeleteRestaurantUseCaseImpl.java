package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantDeleteGateway;
import pos.java.bora_comer.core.usercase.restaurant.DeleteRestaurantUseCase;

@Service
public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {

    private final RestaurantDeleteGateway restaurantDeleteGateway;

    public DeleteRestaurantUseCaseImpl(RestaurantDeleteGateway restaurantDeleteGateway) {
        this.restaurantDeleteGateway = restaurantDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        restaurantDeleteGateway.deleteById(id);
    }
}
