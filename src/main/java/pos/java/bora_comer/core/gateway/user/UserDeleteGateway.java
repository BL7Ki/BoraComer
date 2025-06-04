package pos.java.bora_comer.core.gateway.user;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface UserDeleteGateway {

    boolean existsById(Long id) throws SummerNotFoundException;
    void deleteById(Long id) throws SummerNotFoundException;
}
