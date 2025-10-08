package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

public interface CreateUserControllerDocs {

    @Operation(
            summary = "Criar novo usuário",
            description = "Endpoint para cadastrar um novo usuário. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação do usuário",
            content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
    )
    ResponseEntity<UserResponseDTO> create(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            UserRequestDTO userRequestDTO
    );
}

