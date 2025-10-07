package pos.java.bora_comer.infra.delivery.restaurant.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;

public interface UpdateRestaurantControllerDocs {

    @Operation(
            summary = "Atualizar restaurante",
            description = "Endpoint para atualizar os dados de um restaurante. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @RequestBody(
            required = true,
            description = "Dados para atualização do restaurante",
            content = @Content(schema = @Schema(implementation = RestaurantUpdateRequestDTO.class))
    )
    ResponseEntity<RestaurantResponseDTO> update(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "id",
                    description = "ID do restaurante",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,
            RestaurantUpdateRequestDTO updateRequestDTO
    );
}
