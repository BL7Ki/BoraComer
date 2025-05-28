package pos.java.bora_comer.infra.delivery.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UppdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

@RestController
@RequestMapping("/users")
public class UppdateUserController {

    private final UppdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;

    public UppdateUserController(UppdateUserUseCase updateUserUseCase, UserMapper userMapper) {
        this.updateUserUseCase = updateUserUseCase;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Endpoint para atualizar um usuário com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)
    ))
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable ("id") Long id,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
            ) {
        User user = userMapper.toDomain(userUpdateRequestDTO, id);

        User updatedUser = updateUserUseCase.execute(user);

        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(updatedUser);

        return ResponseEntity.ok(userResponseDTO);
    }
}
