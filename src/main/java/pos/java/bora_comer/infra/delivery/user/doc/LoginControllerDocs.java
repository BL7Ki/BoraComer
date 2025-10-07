package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;
import pos.java.bora_comer.infra.delivery.login.dto.LoginResponseDTO;

public interface LoginControllerDocs {

    @Operation(
            summary = "Validar login",
            description = "Endpoint para autenticação do usuário. Retorna um token JWT em caso de sucesso."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<LoginResponseDTO> validateLogin(@RequestBody LoginRequestDTO loginRequestDTO);
}
