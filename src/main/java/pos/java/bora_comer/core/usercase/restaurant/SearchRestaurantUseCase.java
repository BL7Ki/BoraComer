package pos.java.bora_comer.core.usercase.restaurant;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchRestaurantUseCase {

    Restaurant findById(Long id) throws SummerNotFoundException;

    Page<Restaurant> findAll(int page, int size) throws RestaurantDomainException;
}
