package pos.java.bora_comer.core.gateway.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantSearchGateway {

    Optional<Restaurant> findById(Long id);
    Page<Restaurant> findAll(Pageable pageable);
}
