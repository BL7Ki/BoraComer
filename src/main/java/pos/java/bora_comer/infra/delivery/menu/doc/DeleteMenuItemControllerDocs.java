package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface DeleteMenuItemControllerDocs {

    @Operation(
            summary = "Excluir Item do Menu por ID",
            description = "Endpoint para remover um Menu do Item específico do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item do Menu excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do Menu não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(Long id);
}
