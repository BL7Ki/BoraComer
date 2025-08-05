package pos.java.bora_comer.core.gateway.restaurant;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;

import java.util.Optional;

public interface RestaurantUpdateGateway {

    Restaurant update(Restaurant restaurant) throws RestaurantDomainException;

    Optional<Restaurant> findById(Long id);
}
