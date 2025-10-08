package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdateMenuItemControllerDocs {

    @Operation(
            summary = "Atualizar item de menu",
            description = "Endpoint para atualizar um item do cardápio. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<MenuItemResponseDTO> update(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "id",
                    description = "ID do item de menu",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,
            @Parameter(
                    name = "updateRequestDTO",
                    description = "Dados para atualização do item de menu",
                    required = true
            )
            @RequestBody MenuItemUpdateRequestDTO updateRequestDTO
    );
}
