package pos.java.bora_comer.core.gateway.pedido;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface PedidoDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
