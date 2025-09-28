package pos.java.bora_comer.core.usercase.pedidoItem;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeletePedidoItemUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
