package pos.java.bora_comer.core.gateway.menu;

import pos.java.bora_comer.core.domain.menu.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {
    MenuItem save(MenuItem item);
    List<MenuItem> findAll();
    Optional<MenuItem> findById(Long id);
    MenuItem update(MenuItem item);
    void deleteById(Long id);
}
