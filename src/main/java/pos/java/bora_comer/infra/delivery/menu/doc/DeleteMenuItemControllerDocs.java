package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface DeleteMenuItemControllerDocs {

    @Operation(
            summary = "Deletar item de menu",
            description = "Endpoint para deletar um item do cardápio. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<Void> delete(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "id",
                    description = "ID do item de menu",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    );
}
