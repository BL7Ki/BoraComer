package pos.java.bora_comer.infra.delivery.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.doc.CreateUserControllerDocs;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class CreateUserController implements CreateUserControllerDocs {

    private final UserMapper userMapper;
    private final CreateUserUseCase createUserUseCase;

    // Construtor manual sem Lombok e sem @Autowired (boa prática)
    public CreateUserController(UserMapper userMapper, CreateUserUseCase createUserUseCase) {
        this.userMapper = userMapper;
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {

        var userDomain = userMapper.toDomain(userRequestDTO);

        User user = createUserUseCase.execute(userDomain);

        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(user);

        // Retorna Location com o URI do novo recurso criado
        URI location = URI.create("/users/" + userResponseDTO.id());
        return ResponseEntity.created(location).body(userResponseDTO);
    }
}
