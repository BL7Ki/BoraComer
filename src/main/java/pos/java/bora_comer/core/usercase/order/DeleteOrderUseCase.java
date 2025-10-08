package pos.java.bora_comer.core.usercase.order;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteOrderUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
