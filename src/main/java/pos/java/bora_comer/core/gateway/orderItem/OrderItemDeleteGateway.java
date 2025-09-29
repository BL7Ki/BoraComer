package pos.java.bora_comer.core.gateway.orderItem;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface OrderItemDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
