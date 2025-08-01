package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdateMenuItemControllerDocs {

    @Operation(
            summary = "Atualizar Item de Menu por ID",
            description = "Endpoint para atualizar os dados de um item de menu específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item do menu atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Item do menu não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados atualizados do item de menu",
            content = @Content(schema = @Schema(implementation = MenuItemUpdateRequestDTO.class))
    )
    ResponseEntity<MenuItemResponseDTO> update(Long id, MenuItemUpdateRequestDTO menuItemUpdateRequestDTO);
}
