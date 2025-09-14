package pos.java.bora_comer.infra.delivery.pedido;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.CreatePedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.doc.CreatePedidoControllerDocs;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;

@RestController
@RequestMapping("/pedidos")
public class CreatePedidoItemController implements CreatePedidoControllerDocs {

    private final PedidoMapper pedidoMapper;
    private final CreatePedidoUseCase createPedidoUseCase;

    public CreatePedidoItemController(PedidoMapper pedidoMapper, CreatePedidoUseCase createPedidoUseCase) {
        this.pedidoMapper = pedidoMapper;
        this.createPedidoUseCase = createPedidoUseCase;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        var pedidoDomain = pedidoMapper.toDomain(pedidoRequestDTO);
        Pedido createdPedido = createPedidoUseCase.execute(pedidoDomain);
        PedidoResponseDTO responseDTO = pedidoMapper.toResponseDTO(createdPedido);
        URI location = URI.create("/pedidos/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
