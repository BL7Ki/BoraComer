package pos.java.bora_comer.infra.delivery.menu;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.CreateMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.doc.CreateMenuItemControllerDocs;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

@RestController
@RequestMapping("/menu-items")
public class CreateMenuItemController implements CreateMenuItemControllerDocs {

    private final MenuItemMapper menuItemMapper;
    private final CreateMenuItemUseCase createMenuItemUseCase;

    public CreateMenuItemController(MenuItemMapper menuItemMapper, CreateMenuItemUseCase createMenuItemUseCase) {
        this.menuItemMapper = menuItemMapper;
        this.createMenuItemUseCase = createMenuItemUseCase;
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<MenuItemResponseDTO> create(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItemDomain = menuItemMapper.toDomain(menuItemRequestDTO);
        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItemDomain);
        MenuItemResponseDTO responseDTO = menuItemMapper.toResponseDTO(createdMenuItem);
        URI location = URI.create("/menu-items/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
