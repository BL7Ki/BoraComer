package pos.java.bora_comer.infra.delivery.order;

import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOrderUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
