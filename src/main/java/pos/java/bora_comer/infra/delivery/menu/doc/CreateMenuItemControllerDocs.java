package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

public interface CreateMenuItemControllerDocs {

    @Operation(
            summary = "Criar um novo item do menu",
            description = "Endpoint para cadastrar um novo item de menu, associando a um restaurante existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item do menu criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação do item do menu",
            content = @Content(schema = @Schema(implementation = MenuItemRequestDTO.class))
    )
    ResponseEntity<MenuItemResponseDTO> create(MenuItemRequestDTO menuItemRequestDTO);
}
