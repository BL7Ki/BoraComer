package pos.java.bora_comer.infra.gateway.restaurant.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.restaurant.RestaurantDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;

@Component
public class RestaurantDeleteGatewayImpl implements RestaurantDeleteGateway {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDeleteGatewayImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Restaurante com ID " + id + " não encontrado."));

        restaurantRepository.delete(restaurantEntity);
    }
}
