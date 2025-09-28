package pos.java.bora_comer.infra.delivery.pedidoItem;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.CreatePedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.doc.CreatePedidoItemControllerDocs;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;

@RestController
@RequestMapping("/pedidoitems")
public class CreatePedidoItemController implements CreatePedidoItemControllerDocs {

    private final PedidoItemMapper pedidoItemMapper;
    private final CreatePedidoItemUseCase createPedidoItemUseCase;

    public CreatePedidoItemController(PedidoItemMapper pedidoItemMapper, CreatePedidoItemUseCase createPedidoItemUseCase) {
        this.pedidoItemMapper = pedidoItemMapper;
        this.createPedidoItemUseCase = createPedidoItemUseCase;
    }

    @PostMapping
    public ResponseEntity<PedidoItemResponseDTO> create(@RequestBody PedidoItemRequestDTO pedidoItemRequestDTO) {
        var pedidoItemDomain = pedidoItemMapper.toDomain(pedidoItemRequestDTO);
        PedidoItem createdPedidoItem = createPedidoItemUseCase.execute(pedidoItemDomain);
        PedidoItemResponseDTO responseDTO = pedidoItemMapper.toResponseDTO(createdPedidoItem);
        URI location = URI.create("/pedidoItems/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
