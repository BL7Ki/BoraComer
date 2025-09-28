package pos.java.bora_comer.infra.delivery.pedidoItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.pedidoItem.DeletePedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.doc.DeletePedidoItemControllerDocs;

@RestController
@RequestMapping("/pedidoitems")
public class DeletePedidoItemController implements DeletePedidoItemControllerDocs {

    private final DeletePedidoItemUseCase deletePedidoItemUseCase;

    public DeletePedidoItemController(DeletePedidoItemUseCase deletePedidoItemUseCase) {
        this.deletePedidoItemUseCase = deletePedidoItemUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePedidoItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
