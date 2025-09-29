package pos.java.bora_comer.infra.delivery.orderItem;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.CreateOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.doc.CreateOrderItemControllerDocs;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;

@RestController
@RequestMapping("/orderitems")
public class CreateOrderItemController implements CreateOrderItemControllerDocs {

    private final OrderItemMapper orderItemMapper;
    private final CreateOrderItemUseCase createOrderItemUseCase;

    public CreateOrderItemController(OrderItemMapper orderItemMapper, CreateOrderItemUseCase createOrderItemUseCase) {
        this.orderItemMapper = orderItemMapper;
        this.createOrderItemUseCase = createOrderItemUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create(@RequestBody OrderItemRequestDTO orderItemRequestDTO) {
        var orderItemDomain = orderItemMapper.toDomain(orderItemRequestDTO);
        OrderItem createdOrderItem = createOrderItemUseCase.execute(orderItemDomain);
        OrderItemResponseDTO responseDTO = orderItemMapper.toResponseDTO(createdOrderItem);
        URI location = URI.create("/orderitems/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
