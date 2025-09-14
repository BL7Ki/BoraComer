package pos.java.bora_comer.infra.delivery.pedido;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.SearchPedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.doc.SearchPedidoControllerDocs;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class SearchPedidoController implements SearchPedidoControllerDocs {

    private final SearchPedidoUseCase searchPedidoUseCase;
    private final PedidoMapper pedidoMapper;

    public SearchPedidoController(SearchPedidoUseCase searchPedidoUseCase, PedidoMapper pedidoMapper) {
        this.searchPedidoUseCase = searchPedidoUseCase;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        var pedido = searchPedidoUseCase.findById(id);
        return ResponseEntity.ok(pedidoMapper.toResponseDTO(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Pedido> pedidos = searchPedidoUseCase.findAll(page, size);

        List<PedidoResponseDTO> responseList = pedidos.stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
