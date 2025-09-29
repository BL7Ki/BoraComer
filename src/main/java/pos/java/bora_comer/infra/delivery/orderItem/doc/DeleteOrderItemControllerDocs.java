package pos.java.bora_comer.infra.delivery.orderItem.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface DeleteOrderItemControllerDocs {

    @Operation(
            summary = "Excluir Item do Pedido por ID",
            description = "Endpoint para remover um Item do Pedido específico do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item do Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(Long id);
}
