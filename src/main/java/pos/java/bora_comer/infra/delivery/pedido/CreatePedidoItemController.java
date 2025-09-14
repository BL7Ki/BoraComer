package pos.java.bora_comer.infra.delivery.pedido;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.CreatePedidoUseCase;
import pos.java.bora_comer.infra.delivery.menu.doc.CreateMenuItemControllerDocs;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

@RestController
@RequestMapping("/menu-items")
public class CreatePedidoItemController implements CreateMenuItemControllerDocs {

    private final MenuItemMapper menuItemMapper;
    private final CreatePedidoUseCase createMenuItemUseCase;

    public CreatePedidoItemController(MenuItemMapper menuItemMapper, CreatePedidoUseCase createMenuItemUseCase) {
        this.menuItemMapper = menuItemMapper;
        this.createMenuItemUseCase = createMenuItemUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> create(@RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItemDomain = menuItemMapper.toDomain(menuItemRequestDTO);
        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItemDomain);
        MenuItemResponseDTO responseDTO = menuItemMapper.toResponseDTO(createdMenuItem);
        URI location = URI.create("/menu-items/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
