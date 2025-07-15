package pos.java.bora_comer.core.gateway.restaurant;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface RestaurantDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
