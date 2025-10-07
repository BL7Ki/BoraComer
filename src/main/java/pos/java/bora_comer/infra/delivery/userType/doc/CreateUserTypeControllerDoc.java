package pos.java.bora_comer.infra.delivery.userType.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

public interface CreateUserTypeControllerDoc {

    @Operation(
            summary = "Criar tipo de usuário",
            description = "Endpoint para criação de um novo tipo de usuário. Requer autenticação via JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<UserTypeResponseDTO> create(
            @Parameter(
                    name = "Authorization",
                    description = "JWT token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization,
            @Parameter(
                    name = "createUserTypeRequestDTO",
                    description = "Dados do tipo de usuário",
                    required = true
            )
            @RequestBody CreateUserTypeRequestDTO createUserTypeRequestDTO
    );
}
