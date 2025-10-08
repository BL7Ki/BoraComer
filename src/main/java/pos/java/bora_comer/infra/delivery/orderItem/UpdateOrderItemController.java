package pos.java.bora_comer.infra.delivery.orderItem;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.UpdateOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.doc.UpdateOrderItemControllerDocs;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;

@RestController
@RequestMapping("/orderitems")
public class UpdateOrderItemController implements UpdateOrderItemControllerDocs {

    private final OrderItemMapper orderItemMapper;
    private final UpdateOrderItemUseCase updateOrderItemUseCase;

    public UpdateOrderItemController(OrderItemMapper orderItemMapper, UpdateOrderItemUseCase updateOrderItemUseCase) {
        this.orderItemMapper = orderItemMapper;
        this.updateOrderItemUseCase = updateOrderItemUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> update(
            @RequestHeader("Authorization") String authorization,@PathVariable Long id,
            @RequestBody OrderItemUpdateRequestDTO updateRequestDTO) {
        // Busca o item do pedido existente para preservar o porderId e menuItemId
        OrderItem existingOrderItem = updateOrderItemUseCase.findById(id);
        var orderItemDomain = orderItemMapper.toDomain(updateRequestDTO, id, existingOrderItem.getOrderId(), existingOrderItem.getMenuItemId());

        OrderItem updatedOrderItem = updateOrderItemUseCase.execute(orderItemDomain);
        OrderItemResponseDTO responseDTO = orderItemMapper.toResponseDTO(updatedOrderItem);
        return ResponseEntity.ok(responseDTO);
    }

}
