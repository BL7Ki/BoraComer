package pos.java.bora_comer.core.gateway.restaurant;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;

public interface RestaurantCreateGateway {

    boolean existsByName(String name);

    Restaurant save(Restaurant restaurant);
}
