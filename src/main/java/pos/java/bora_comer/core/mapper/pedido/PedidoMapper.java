package pos.java.bora_comer.core.mapper.pedido;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;

public interface PedidoMapper {

    MenuItem toDomain(MenuItemRequestDTO menuItemRequestDTO);
    
    MenuItemEntity toEntity(MenuItem menuItem);

    MenuItem toDomain(MenuItemEntity menuItemEntity);

    MenuItemResponseDTO toResponseDTO(MenuItem menuItem);

    MenuItem toDomain(MenuItemUpdateRequestDTO menuItemUpdateRequestDTO, Long id, Long restaurantId);
}
