package pos.java.bora_comer.infra.delivery.reserve.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;

import org.springframework.http.ResponseEntity;

public interface CreateReserveControllerDocs {

    @Operation(
            summary = "Criar uma nova reserva",
            description = "Endpoint para cadastrar uma nova reserva, associando a um restaurante e usuário existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação de Reserva",
            content = @Content(schema = @Schema(implementation = ReserveRequestDTO.class))
    )
    ResponseEntity<ReserveResponseDTO> create(ReserveRequestDTO reserveRequestDTO);
}
