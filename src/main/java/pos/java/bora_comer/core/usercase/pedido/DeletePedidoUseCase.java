package pos.java.bora_comer.core.usercase.pedido;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeletePedidoUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
