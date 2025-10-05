package pos.java.bora_comer.infra.delivery.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.doc.UpdateOrderControllerDocs;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

@RestController
@RequestMapping("/orders")
public class UpdateOrderController implements UpdateOrderControllerDocs {

    private final OrderMapper orderMapper;
    private final UpdateOrderUseCase updateOrderUseCase;

    public UpdateOrderController(OrderMapper orderMapper, UpdateOrderUseCase updateOrderUseCase) {
        this.orderMapper = orderMapper;
        this.updateOrderUseCase = updateOrderUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id,
                                                   @RequestBody OrderUpdateRequestDTO updateRequestDTO) {

        Order updatedOrder = updateOrderUseCase.execute(id, updateRequestDTO);

        OrderResponseDTO responseDTO = orderMapper.toResponseDTO(updatedOrder);

        return ResponseEntity.ok(responseDTO);
    }
}