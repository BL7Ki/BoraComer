package pos.java.bora_comer.infra.delivery.restaurant.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;

public interface UpdateRestaurantControllerDocs {

    @Operation(
            summary = "Atualizar restaurante por ID",
            description = "Endpoint para atualizar os dados de um restaurante específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados atualizados do restaurante",
            content = @Content(schema = @Schema(implementation = RestaurantUpdateRequestDTO.class))
    )
    ResponseEntity<RestaurantResponseDTO> update(Long id, RestaurantUpdateRequestDTO restaurantUpdateRequestDTO);
}
