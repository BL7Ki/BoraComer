package pos.java.bora_comer.infra.delivery.userType.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import java.util.List;

public interface SearchUserTypeControllerDoc {

    @Operation(
            summary = "Buscar tipos de usuário com paginação",
            description = "Retorna uma lista paginada de tipos de usuário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    ResponseEntity<List<UserTypeResponseDTO>> findAll(
            @Parameter(description = "Número da página", example = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") int size
    );
}
