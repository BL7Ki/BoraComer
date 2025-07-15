package pos.java.bora_comer.infra.delivery.user.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pos.java.bora_comer.infra.delivery.user.dto.UserChangePasswordRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import java.util.Map;

public interface UppdateUserControllerDocs {

    @Operation(
            summary = "Atualizar um usuário",
            description = "Endpoint para atualizar um usuário com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<UserResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
    );

    @Operation(
            summary = "Trocar senha do usuário",
            description = "Endpoint para trocar a senha de um usuário com base no ID fornecido."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Senha alterada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"message\": \"Senha alterada com sucesso.\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<Map<String, String>> changePassword(
            @PathVariable("id") Long id,
            @RequestBody UserChangePasswordRequestDTO request
    );

    @Operation(
            summary = "Associa um tipo de usuário ao usuário",
            description = "Associa o usuário identificado por userId ao tipo de usuário identificado por tipoUsuarioId. Retorna o usuário atualizado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Usuário ou tipo de usuário não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PutMapping("/{userId}/tipo-usuario/{tipoUsuarioId}")
    ResponseEntity<UserResponseDTO> userTypeAssociate(
            @PathVariable("userId") Long userId,
            @PathVariable("tipoUsuarioId") Long tipoUsuarioId
    );
}
