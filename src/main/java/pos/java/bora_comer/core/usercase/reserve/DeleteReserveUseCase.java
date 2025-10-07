package pos.java.bora_comer.core.usercase.reserve;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteReserveUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
