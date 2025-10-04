package pos.java.bora_comer.infra.delivery.reserve.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdateReserveControllerDocs {

    @Operation(
            summary = "Atualizar Reserva por ID",
            description = "Endpoint para atualizar os dados de una reserva específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados atualizados do pedido",
            content = @Content(schema = @Schema(implementation = ReserveUpdateRequestDTO.class))
    )
    ResponseEntity<ReserveResponseDTO> update(Long id, ReserveUpdateRequestDTO reserveUpdateRequestDTO);
}
