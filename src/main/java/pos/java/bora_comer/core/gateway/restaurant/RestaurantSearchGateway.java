package pos.java.bora_comer.core.gateway.restaurant;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;

public interface RestaurantSearchGateway {

    Restaurant findById(Long id);

    Page<Restaurant> findAll(int page, int size, String cuisineType);
}
