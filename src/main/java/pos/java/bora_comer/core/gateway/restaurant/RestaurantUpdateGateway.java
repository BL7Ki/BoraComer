package pos.java.bora_comer.core.gateway.restaurant;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;

public interface RestaurantUpdateGateway {

    Restaurant update(Restaurant restaurant) throws RestaurantDomainException;
}
