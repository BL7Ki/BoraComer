package pos.java.bora_comer.infra.delivery.reserve.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface DeleteReserveControllerDocs {

    @Operation(
            summary = "Excluir reserva por ID",
            description = "Endpoint para remover uma Reserva específica do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(Long id);
}
