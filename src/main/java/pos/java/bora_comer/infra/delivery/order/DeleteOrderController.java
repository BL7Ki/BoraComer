package pos.java.bora_comer.infra.delivery.order;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.usercase.order.DeleteOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.doc.DeleteOrderControllerDocs;

@RestController
@RequestMapping("/orders")
public class DeleteOrderController implements DeleteOrderControllerDocs {

    private final DeleteOrderUseCase deleteOrderUseCase;

    public DeleteOrderController(DeleteOrderUseCase deleteOrderUseCase) {
        this.deleteOrderUseCase = deleteOrderUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        deleteOrderUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
