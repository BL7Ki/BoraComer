package pos.java.bora_comer.infra.delivery.userType;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;
import pos.java.bora_comer.infra.delivery.userType.doc.CreateUserTypeControllerDoc;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import java.net.URI;

@RestController
@RequestMapping("/user-types")
public class CreateUserTypeController implements CreateUserTypeControllerDoc {

    private final UserTypeMapper userTypeMapper;

    private final CreateUserTypeUseCase createUserTypeUseCase;

    public CreateUserTypeController(UserTypeMapper userTypeMapper, CreateUserTypeUseCase createUserTypeUseCase) {
        this.userTypeMapper = userTypeMapper;
        this.createUserTypeUseCase = createUserTypeUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> create(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody CreateUserTypeRequestDTO createUserTypeRequestDTO
    ) {

        UserType userTypeDomain = userTypeMapper.toDomain(createUserTypeRequestDTO.name());

        UserType userType = createUserTypeUseCase.execute(userTypeDomain);

        URI location = URI.create("/user-types/" + userType.getId());

        UserTypeResponseDTO userTypeResponseDTO = userTypeMapper.toResponse(userType);

        return ResponseEntity.created(location).body(userTypeResponseDTO);
    }
}
