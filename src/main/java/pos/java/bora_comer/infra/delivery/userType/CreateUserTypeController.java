package pos.java.bora_comer.infra.delivery.userType;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import java.net.URI;

@RestController
@RequestMapping("/user-types")
public class CreateUserTypeController {

    private final UserTypeMapper userTypeMapper;

    private final CreateUserTypeUseCase createUserTypeUseCase;

    public CreateUserTypeController(UserTypeMapper userTypeMapper, CreateUserTypeUseCase createUserTypeUseCase) {
        this.userTypeMapper = userTypeMapper;
        this.createUserTypeUseCase = createUserTypeUseCase;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> create(
            @Valid @RequestBody CreateUserTypeRequestDTO createUserTypeRequestDTO
    ) {

        UserType userTypeDomain = userTypeMapper.toDomain(createUserTypeRequestDTO.name());

        UserType userType = createUserTypeUseCase.execute(userTypeDomain);

        URI location = URI.create("/user-types/" + userType.getId());

        UserTypeResponseDTO userTypeResponseDTO = userTypeMapper.toResponse(userType);

        return ResponseEntity.created(location).body(userTypeResponseDTO);
    }
}
