package pos.java.bora_comer.infra.delivery.user;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.UserMapper;
import pos.java.bora_comer.core.usercase.SearchUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SearchUserController {

    private final SearchUserUseCase searchUserUseCase;
    private final UserMapper userMapper;

    public SearchUserController(SearchUserUseCase searchUserUseCase, UserMapper userMapper) {
        this.searchUserUseCase = searchUserUseCase;
        this.userMapper = userMapper;
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        var user = searchUserUseCase.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {

        Page<User> users = searchUserUseCase.findAll(page, size);
        List<UserResponseDTO> usuarios = users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}
