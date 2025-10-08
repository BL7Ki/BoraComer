package pos.java.bora_comer.core.usercase.orderItem;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteOrderItemUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
