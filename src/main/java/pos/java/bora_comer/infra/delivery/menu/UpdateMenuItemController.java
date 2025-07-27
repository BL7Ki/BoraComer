package pos.java.bora_comer.infra.delivery.menu;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.UpdateMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.doc.UpdateMenuItemControllerDocs;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;


@RestController
@RequestMapping("/menuitems")
public class UpdateMenuItemController implements UpdateMenuItemControllerDocs {

    private final MenuItemMapper menuItemMapper;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;

    public UpdateMenuItemController(MenuItemMapper menuItemMapper, UpdateMenuItemUseCase updateMenuItemUseCase) {
        this.menuItemMapper = menuItemMapper;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody MenuItemUpdateRequestDTO updateRequestDTO) {
        // Busca o item do menu existente para preservar o restaurantId
        MenuItem existingMenuItem = updateMenuItemUseCase.findById(id);
        var menuItemDomain = menuItemMapper.toDomain(updateRequestDTO, id, existingMenuItem.getRestaurantId());

        MenuItem updatedMenuItem = updateMenuItemUseCase.execute(menuItemDomain);
        MenuItemResponseDTO responseDTO = menuItemMapper.toResponseDTO(updatedMenuItem);
        return ResponseEntity.ok(responseDTO);
    }

}
