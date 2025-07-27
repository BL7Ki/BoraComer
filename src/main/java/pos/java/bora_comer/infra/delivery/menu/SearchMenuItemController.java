package pos.java.bora_comer.infra.delivery.menu;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.SearchMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.doc.SearchMenuItemControllerDocs;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/menuitems")
public class SearchMenuItemController implements SearchMenuItemControllerDocs {

    private final SearchMenuItemUseCase searchMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    public SearchMenuItemController(SearchMenuItemUseCase searchMenuItemUseCase, MenuItemMapper menuItemMapper) {
        this.searchMenuItemUseCase = searchMenuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> findById(@PathVariable Long id) {
        var menuItem = searchMenuItemUseCase.findById(id);
        return ResponseEntity.ok(menuItemMapper.toResponseDTO(menuItem));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<MenuItem> menuItems = searchMenuItemUseCase.findAll(page, size);

        List<MenuItemResponseDTO> responseList = menuItems.stream()
                .map(menuItemMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
