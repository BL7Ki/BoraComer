package pos.java.bora_comer.infra.delivery.reserve.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchReserveControllerDocs {

    @Operation(
            summary = "Buscar Reserva por ID",
            description = "Endpoint para buscar uma Reserva com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Reserva encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReserveResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Reserva não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<ReserveResponseDTO> findById(@PathVariable Long id);

    @Operation(
            summary = "Buscar todas as reservas",
            description = "Endpoint para buscar todas as reservas com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Reservas encontradas com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReserveResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<ReserveResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
