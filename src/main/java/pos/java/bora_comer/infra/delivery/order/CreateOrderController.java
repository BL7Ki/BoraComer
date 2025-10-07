package pos.java.bora_comer.infra.delivery.order;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.doc.CreateOrderControllerDocs;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;

@RestController
@RequestMapping("/orders")
public class CreateOrderController implements CreateOrderControllerDocs {

    private final OrderMapper orderMapper;
    private final CreateOrderUseCase createOrderUseCase;

    public CreateOrderController(OrderMapper orderMapper, CreateOrderUseCase createOrderUseCase) {
        this.orderMapper = orderMapper;
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO orderRequestDTO) {
        var orderDomain = orderMapper.toDomain(orderRequestDTO);
        Order createdOrder = createOrderUseCase.execute(orderDomain);
        OrderResponseDTO responseDTO = orderMapper.toResponseDTO(createdOrder);
        URI location = URI.create("/orders/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
