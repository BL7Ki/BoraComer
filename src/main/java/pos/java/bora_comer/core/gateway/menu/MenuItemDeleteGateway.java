package pos.java.bora_comer.core.gateway.menu;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface MenuItemDeleteGateway {

    void deleteById(Long id) throws SummerNotFoundException;
}
