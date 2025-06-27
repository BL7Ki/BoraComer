package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

import java.util.List;

public interface SearchUserControllerDocs {

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Endpoint para buscar um usuário com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<UserResponseDTO> findById(@PathVariable Long id);

    @Operation(
            summary = "Buscar todos os usuários",
            description = "Endpoint para buscar todos os usuários com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<UserResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
