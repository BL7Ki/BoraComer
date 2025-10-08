package pos.java.bora_comer.infra.delivery.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.login.LoginEnum;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.doc.UppdateUserControllerDocs;
import pos.java.bora_comer.infra.delivery.user.dto.UserChangePasswordRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UpdateUserController implements UppdateUserControllerDocs {

    private final UpdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;


    public UpdateUserController(UpdateUserUseCase updateUserUseCase, UserMapper userMapper) {
        this.updateUserUseCase = updateUserUseCase;
        this.userMapper = userMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") Long id,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
            ) {
        User user = userMapper.toDomain(userUpdateRequestDTO, id);

        User updatedUser = updateUserUseCase.execute(user);

        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(updatedUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") Long id,
            @RequestBody @Valid UserChangePasswordRequestDTO request
    ) {
        updateUserUseCase.changeUserPassword(id, request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(
                Map.of("message", LoginEnum.PASSWORD_CHANGED_SUCCESSFULLY.getMessage())
        );

    }

    // mudar o retorno para UserResponseDTO
    // 200 OK com corpo: Retornar o recurso atualizado (ex: o usuário já associado ao novo tipo), permitindo ao cliente ver o estado final.

    @PreAuthorize("isAuthenticated()")
    @Override
    @PutMapping("/{userId}/tipo-usuario/{tipoUsuarioId}")
    public ResponseEntity<UserResponseDTO> userTypeAssociate(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("userId") Long userId,
            @PathVariable("tipoUsuarioId") Long tipoUsuarioId
    ) {

        User user = updateUserUseCase.userAssociate(userId, tipoUsuarioId);

        return ResponseEntity.ok()
                .body(userMapper.toResponseDTO(user));
    }
}
