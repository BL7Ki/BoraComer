package pos.java.bora_comer.infra.delivery.userType.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

public interface CreateUserTypeControllerDoc {

    @Operation(
            summary = "Criar um novo tipo de usuário",
            description = "Endpoint para criar um novo tipo de usuário com base nos dados fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    ResponseEntity<UserTypeResponseDTO> create(
            @RequestBody(
                    required = true,
                    description = "Dados para criação do tipo de usuário. O campo 'name' aceita apenas: DONO_RESTAURANTE ou CLIENTE.",
                    content = @Content(schema = @Schema(implementation = CreateUserTypeRequestDTO.class))
            )
            CreateUserTypeRequestDTO createUserTypeRequestDTO
    );
}
