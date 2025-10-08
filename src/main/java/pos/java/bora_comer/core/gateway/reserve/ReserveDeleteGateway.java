package pos.java.bora_comer.core.gateway.reserve;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface ReserveDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
