package pos.java.bora_comer.core.gateway.menu;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.menu.MenuItem;


public interface MenuItemSearchGateway {

    MenuItem findById(Long id);

    Page<MenuItem> findAll(int page, int size);
}
