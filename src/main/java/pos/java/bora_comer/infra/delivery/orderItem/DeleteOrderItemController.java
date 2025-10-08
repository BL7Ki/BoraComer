package pos.java.bora_comer.infra.delivery.orderItem;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.usercase.orderItem.DeleteOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.doc.DeleteOrderItemControllerDocs;

@RestController
@RequestMapping("/orderitems")
public class DeleteOrderItemController implements DeleteOrderItemControllerDocs {

    private final DeleteOrderItemUseCase deleteOrderItemUseCase;

    public DeleteOrderItemController(DeleteOrderItemUseCase deleteOrderItemUseCase) {
        this.deleteOrderItemUseCase = deleteOrderItemUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        deleteOrderItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
