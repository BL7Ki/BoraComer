package pos.java.bora_comer.core.usercase.restaurant.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantUpdateGateway;
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;

@Service
public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {

    private final RestaurantUpdateGateway restaurantUpdateGateway;

    public UpdateRestaurantUseCaseImpl(RestaurantUpdateGateway restaurantUpdateGateway) {
        this.restaurantUpdateGateway = restaurantUpdateGateway;
    }

    @Override
    public Restaurant execute(Restaurant restaurant) {
        return restaurantUpdateGateway.update(restaurant);
    }

    @Override
    public Restaurant findById(Long id) throws RestaurantDomainException {
        return restaurantUpdateGateway.findById(id)
                .orElseThrow(() -> new RestaurantDomainException("Restaurante com ID " + id + " não encontrado"));
    }
}
