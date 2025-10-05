package pos.java.bora_comer.infra.delivery.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
    ) {
        User user = userMapper.toDomain(userUpdateRequestDTO, id);

        User updatedUser = updateUserUseCase.execute(user);

        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(updatedUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @Override
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody @Valid UserChangePasswordRequestDTO request
    ) {
        updateUserUseCase.changeUserPassword(
                request.username(),
                request.currentPassword(),
                request.newPassword()
        );
        return ResponseEntity.ok(
                Map.of("message", "Senha alterada com sucesso.")
        );
    }

    @Override
    @PutMapping("/{userId}/tipo-usuario/{tipoUsuarioId}")
    public ResponseEntity<UserResponseDTO> userTypeAssociate(
            @PathVariable("userId") Long userId,
            @PathVariable("tipoUsuarioId") Long tipoUsuarioId
    ) {

        User user = updateUserUseCase.userAssociate(userId, tipoUsuarioId);

        return ResponseEntity.ok()
                .body(userMapper.toResponseDTO(user));
    }
}