package pos.java.bora_comer.infra.delivery.pedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.UpdatePedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.doc.UpdatePedidoControllerDocs;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;

@RestController
@RequestMapping("/pedidos")
public class UpdatePedidoController implements UpdatePedidoControllerDocs {

    private final PedidoMapper pedidoMapper;
    private final UpdatePedidoUseCase updatePedidoUseCase;

    public UpdatePedidoController(PedidoMapper pedidoMapper, UpdatePedidoUseCase updatePedidoUseCase) {
        this.pedidoMapper = pedidoMapper;
        this.updatePedidoUseCase = updatePedidoUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody PedidoUpdateRequestDTO updateRequestDTO) {
        // Busca o pedido existente para preservar o restaurantId e userId
        Pedido existingPedido = updatePedidoUseCase.findById(id);
        var pedidoDomain = pedidoMapper.toDomain(updateRequestDTO, id, existingPedido.getRestaurantId(), existingPedido.getUserId());

        Pedido updatedPedido = updatePedidoUseCase.execute(pedidoDomain);
        PedidoResponseDTO responseDTO = pedidoMapper.toResponseDTO(updatedPedido);
        return ResponseEntity.ok(responseDTO);
    }

}
