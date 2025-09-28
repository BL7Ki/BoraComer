package pos.java.bora_comer.core.gateway.pedidoItem;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface PedidoItemDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
