package pos.java.bora_comer.infra.delivery.restaurant.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface DeleteRestaurantControllerDocs {

    @Operation(
            summary = "Excluir restaurante por ID",
            description = "Endpoint para remover um restaurante específico do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(Long id);
}
