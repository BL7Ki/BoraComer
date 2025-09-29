package pos.java.bora_comer.infra.delivery.order.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface DeleteOrderControllerDocs {

    @Operation(
            summary = "Excluir Pedido por ID",
            description = "Endpoint para remover um Pedido específico do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(Long id);
}
