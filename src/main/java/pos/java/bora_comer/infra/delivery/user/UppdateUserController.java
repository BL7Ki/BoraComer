package pos.java.bora_comer.infra.delivery.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UppdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.doc.UppdateUserControllerDocs;
import pos.java.bora_comer.infra.delivery.user.dto.UserChangePasswordRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UppdateUserController implements UppdateUserControllerDocs {

    private final UppdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;

    public UppdateUserController(UppdateUserUseCase updateUserUseCase, UserMapper userMapper) {
        this.updateUserUseCase = updateUserUseCase;
        this.userMapper = userMapper;
    }

    @Override
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

    @Override
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserChangePasswordRequestDTO request
    ) {
        updateUserUseCase.changeUserPassword(id, request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(
                Map.of("message", LoginResponseEnum.PASSWORD_CHANGED_SUCCESSFULLY.getMessage())
        );

    }
}
