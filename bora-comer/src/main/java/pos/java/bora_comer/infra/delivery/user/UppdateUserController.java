package pos.java.bora_comer.infra.delivery.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.UserMapper;
import pos.java.bora_comer.core.usercase.UppdateUserUseCase;
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
