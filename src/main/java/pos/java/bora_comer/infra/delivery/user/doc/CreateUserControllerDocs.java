package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

public interface CreateUserControllerDocs {

    @Operation(
            summary = "Criar um novo usuário",
            description = "Endpoint para criar um novo usuário. É obrigatório o papel (ADMIN ou DEFAULT). Tipo de usuário opcional, mas se enviado, deve ser um dos tipos válidos. DONO_RESTAURANTE ou CLIENTE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação do usuário. O campo 'papel' é obrigatório e só aceita: ADMIN ou DEFAULT.",
            content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
    )
    ResponseEntity<UserResponseDTO> create(UserRequestDTO userRequestDTO);
}

