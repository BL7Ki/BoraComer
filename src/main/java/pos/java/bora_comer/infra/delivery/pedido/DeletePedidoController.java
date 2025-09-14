package pos.java.bora_comer.infra.delivery.pedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.pedido.DeletePedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.doc.DeletePedidoControllerDocs;

@RestController
@RequestMapping("/pedidos")
public class DeletePedidoController implements DeletePedidoControllerDocs {

    private final DeletePedidoUseCase deletePedidoUseCase;

    public DeletePedidoController(DeletePedidoUseCase deletePedidoUseCase) {
        this.deletePedidoUseCase = deletePedidoUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePedidoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
