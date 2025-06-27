package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;

public interface LoginControllerDocs {

    @Operation(
            summary = "Validar login",
            description = "Endpoint para validar o login do usuário com base no nome de usuário e senha fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login válido"),
            @ApiResponse(responseCode = "401", description = "Login inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequestDTO.class)
                    )
            )
    })
    ResponseEntity<String> validateLogin(@RequestBody LoginRequestDTO loginRequestDTO);
}
