package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

public interface CreateMenuItemControllerDocs {

    @Operation(
            summary = "Criar item de menu",
            description = "Endpoint para criar um novo item no cardápio. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @RequestBody(
            required = true,
            description = "Dados do item de menu",
            content = @Content(schema = @Schema(implementation = MenuItemRequestDTO.class))
    )
    ResponseEntity<MenuItemResponseDTO> create(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            MenuItemRequestDTO menuItemRequestDTO
    );
}
