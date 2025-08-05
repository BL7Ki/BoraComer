package pos.java.bora_comer.infra.gateway.menu.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.menu.MenuItemDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;

@Component
public class MenuItemDeleteGatewayImpl implements MenuItemDeleteGateway {

    private final MenuItemRepository menuItemRepository;

    public MenuItemDeleteGatewayImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var menuItemEntity = menuItemRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Item do menu com ID " + id + " não encontrado."));

        menuItemRepository.delete(menuItemEntity);
    }
}
