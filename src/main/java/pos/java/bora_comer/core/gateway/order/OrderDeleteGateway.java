package pos.java.bora_comer.core.gateway.order;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface OrderDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
