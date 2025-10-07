package pos.java.bora_comer.infra.delivery.restaurant.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;

import java.util.List;

public interface SearchRestaurantControllerDocs {

    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Endpoint para buscar um restaurante com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestaurantResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<RestaurantResponseDTO> findById(
            @PathVariable Long id,
            @Parameter(
                    name = "Authorization",
                    description = "Token JWT para autenticação",
                    required = true,
                    in = ParameterIn.HEADER,
                    example = "Bearer SEU_TOKEN_JWT"
            )
            @RequestHeader("Authorization") String authorization
    );

    @Operation(
            summary = "Buscar todos os restaurantes",
            description = "Endpoint para buscar todos os restaurantes com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Restaurantes encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestaurantResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<RestaurantResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(
                    name = "Authorization",
                    description = "Token JWT para autenticação",
                    required = true,
                    in = ParameterIn.HEADER,
                    example = "Bearer SEU_TOKEN_JWT"
            )
            @RequestHeader("Authorization") String authorization
    );
}
