package pos.java.bora_comer.infra.delivery.order;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.doc.SearchOrderControllerDocs;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class SearchOrderController implements SearchOrderControllerDocs {

    private final SearchOrderUseCase searchOrderUseCase;
    private final OrderMapper orderMapper;

    public SearchOrderController(SearchOrderUseCase searchOrderUseCase, OrderMapper orderMapper) {
        this.searchOrderUseCase = searchOrderUseCase;
        this.orderMapper = orderMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        var order = searchOrderUseCase.findById(id);
        return ResponseEntity.ok(orderMapper.toResponseDTO(order));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Order> orders = searchOrderUseCase.findAll(page, size);

        List<OrderResponseDTO> responseList = orders.stream()
                .map(orderMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
