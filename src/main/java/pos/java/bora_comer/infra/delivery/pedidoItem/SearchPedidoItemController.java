package pos.java.bora_comer.infra.delivery.pedidoItem;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.SearchPedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.doc.SearchPedidoItemControllerDocs;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/pedidoitems")
public class SearchPedidoItemController implements SearchPedidoItemControllerDocs {

    private final SearchPedidoItemUseCase searchPedidoItemUseCase;
    private final PedidoItemMapper pedidoItemMapper;

    public SearchPedidoItemController(SearchPedidoItemUseCase searchPedidoItemUseCase, PedidoItemMapper pedidoItemMapper) {
        this.searchPedidoItemUseCase = searchPedidoItemUseCase;
        this.pedidoItemMapper = pedidoItemMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItemResponseDTO> findById(@PathVariable Long id) {
        var pedidoItem = searchPedidoItemUseCase.findById(id);
        return ResponseEntity.ok(pedidoItemMapper.toResponseDTO(pedidoItem));
    }

    @GetMapping
    public ResponseEntity<List<PedidoItemResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<PedidoItem> pedidos = searchPedidoItemUseCase.findAll(page, size);

        List<PedidoItemResponseDTO> responseList = pedidos.stream()
                .map(pedidoItemMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
