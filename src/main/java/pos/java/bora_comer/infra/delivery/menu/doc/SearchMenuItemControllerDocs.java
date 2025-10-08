package pos.java.bora_comer.infra.delivery.menu.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchMenuItemControllerDocs {

    @Operation(
            summary = "Buscar item de menu por ID",
            description = "Retorna os detalhes de um item do cardápio pelo seu ID. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<MenuItemResponseDTO> findById(
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
            @PathVariable Long id
    );

    @Operation(
            summary = "Listar itens de menu",
            description = "Retorna uma lista paginada de itens do cardápio. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<MenuItemResponseDTO>> findAll(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "page",
                    description = "Número da página",
                    required = false,
                    example = "0"
            )
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(
                    name = "size",
                    description = "Tamanho da página",
                    required = false,
                    example = "10"
            )
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
