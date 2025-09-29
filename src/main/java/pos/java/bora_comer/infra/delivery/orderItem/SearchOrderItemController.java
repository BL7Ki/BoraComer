package pos.java.bora_comer.infra.delivery.orderItem;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.SearchOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.doc.SearchOrderItemControllerDocs;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
public class SearchOrderItemController implements SearchOrderItemControllerDocs {

    private final SearchOrderItemUseCase searchOrderItemUseCase;
    private final OrderItemMapper orderItemMapper;

    public SearchOrderItemController(SearchOrderItemUseCase searchOrderItemUseCase, OrderItemMapper orderItemMapper) {
        this.searchOrderItemUseCase = searchOrderItemUseCase;
        this.orderItemMapper = orderItemMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> findById(@PathVariable Long id) {
        var orderItem = searchOrderItemUseCase.findById(id);
        return ResponseEntity.ok(orderItemMapper.toResponseDTO(orderItem));
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<OrderItem> orders = searchOrderItemUseCase.findAll(page, size);

        List<OrderItemResponseDTO> responseList = orders.stream()
                .map(orderItemMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
