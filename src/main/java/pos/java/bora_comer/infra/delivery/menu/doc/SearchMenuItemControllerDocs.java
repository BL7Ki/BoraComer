package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchMenuItemControllerDocs {

    @Operation(
            summary = "Buscar Item do Menu por ID",
            description = "Endpoint para buscar um Item de Menu com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Item do Menu encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MenuItemResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Item do Menu não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<MenuItemResponseDTO> findById(@PathVariable Long id);

    @Operation(
            summary = "Buscar todos os item de menu",
            description = "Endpoint para buscar todos os item de menu com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Itens do Menu encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MenuItemResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<MenuItemResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
