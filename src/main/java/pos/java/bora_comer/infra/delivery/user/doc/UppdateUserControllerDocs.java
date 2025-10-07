package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import pos.java.bora_comer.infra.delivery.user.dto.UserChangePasswordRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import java.util.Map;

public interface UppdateUserControllerDocs {

    @Operation(
        summary = "Atualizar usuário",
        description = "Endpoint para atualizar os dados de um usuário. Requer autenticação via JWT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @RequestBody(
        required = true,
        description = "Dados para atualização do usuário",
        content = @Content(schema = @Schema(implementation = UserUpdateRequestDTO.class))
    )
    ResponseEntity<UserResponseDTO> update(
        @Parameter(
            name = "Authorization",
            description = "JWT token",
            required = true,
            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        @RequestHeader("Authorization") String authorization,
        @Parameter(
            name = "id",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable("id") Long id,
        UserUpdateRequestDTO userUpdateRequestDTO
    );

    @Operation(
        summary = "Alterar senha do usuário",
        description = "Endpoint para alterar a senha do usuário. Requer autenticação via JWT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @RequestBody(
        required = true,
        description = "Dados para alteração de senha",
        content = @Content(schema = @Schema(implementation = UserChangePasswordRequestDTO.class))
    )
    ResponseEntity<Map<String, String>> changePassword(
        @Parameter(
            name = "Authorization",
            description = "JWT token",
            required = true,
            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        @RequestHeader("Authorization") String authorization,
        @Parameter(
            name = "id",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable("id") Long id,
        UserChangePasswordRequestDTO request
    );

    @Operation(
        summary = "Associar tipo de usuário",
        description = "Endpoint para associar um tipo ao usuário. Requer autenticação via JWT."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuário associado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário ou tipo não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<UserResponseDTO> userTypeAssociate(
        @Parameter(
            name = "Authorization",
            description = "JWT token",
            required = true,
            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        @RequestHeader("Authorization") String authorization,
        @Parameter(
            name = "userId",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable("userId") Long userId,
        @Parameter(
            name = "tipoUsuarioId",
            description = "ID do tipo de usuário",
            required = true,
            example = "2"
        )
        @PathVariable("tipoUsuarioId") Long tipoUsuarioId
    );
}