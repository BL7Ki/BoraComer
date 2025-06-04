package pos.java.bora_comer.core.gateway.user;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface UserDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
