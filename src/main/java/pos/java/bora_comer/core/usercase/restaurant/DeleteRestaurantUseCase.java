package pos.java.bora_comer.core.usercase.restaurant;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteRestaurantUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
