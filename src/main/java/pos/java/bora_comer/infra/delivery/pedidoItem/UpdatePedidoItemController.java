package pos.java.bora_comer.infra.delivery.pedidoItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.UpdatePedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.doc.UpdatePedidoItemControllerDocs;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;

@RestController
@RequestMapping("/pedidoitems")
public class UpdatePedidoItemController implements UpdatePedidoItemControllerDocs {

    private final PedidoItemMapper pedidoItemMapper;
    private final UpdatePedidoItemUseCase updatePedidoItemUseCase;

    public UpdatePedidoItemController(PedidoItemMapper pedidoItemMapper, UpdatePedidoItemUseCase updatePedidoItemUseCase) {
        this.pedidoItemMapper = pedidoItemMapper;
        this.updatePedidoItemUseCase = updatePedidoItemUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoItemResponseDTO> update(@PathVariable Long id,
                                                        @RequestBody PedidoItemUpdateRequestDTO updateRequestDTO) {
        // Busca o item do pedido existente para preservar o pedidoId e menuItemId
        PedidoItem existingPedidoItem = updatePedidoItemUseCase.findById(id);
        var pedidoItemDomain = pedidoItemMapper.toDomain(updateRequestDTO, id, existingPedidoItem.getPedidoId(), existingPedidoItem.getMenuItemId());

        PedidoItem updatedPedidoItem = updatePedidoItemUseCase.execute(pedidoItemDomain);
        PedidoItemResponseDTO responseDTO = pedidoItemMapper.toResponseDTO(updatedPedidoItem);
        return ResponseEntity.ok(responseDTO);
    }

}
