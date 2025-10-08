package pos.java.bora_comer.infra.delivery.reserve.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;

import org.springframework.http.ResponseEntity;

public interface CreateReserveControllerDocs {

    @Operation(
            summary = "Criar reserva",
            description = "Endpoint para criação de uma nova reserva. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<ReserveResponseDTO> create(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "reserveRequestDTO",
                    description = "Dados da reserva",
                    required = true
            )
            @RequestBody ReserveRequestDTO reserveRequestDTO
    );
}
