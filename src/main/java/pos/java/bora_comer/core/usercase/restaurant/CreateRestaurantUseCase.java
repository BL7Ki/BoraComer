package pos.java.bora_comer.core.usercase.restaurant;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;

public interface CreateRestaurantUseCase {

    Restaurant execute(Restaurant restaurant) throws RestaurantDomainException;
}
