package pos.java.bora_comer.infra.delivery.userType;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/user-types/search")
public class SearchUserTypeController {

    private final SearchUserTypeUseCase searchUserTypeUseCase;
    private final UserTypeMapper userTypeMapper;

    public SearchUserTypeController(SearchUserTypeUseCase searchUserTypeUseCase, UserTypeMapper userTypeMapper) {
        this.searchUserTypeUseCase = searchUserTypeUseCase;
        this.userTypeMapper = userTypeMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<UserTypeResponseDTO>> findAll(
            @RequestHeader("Authorization") String authorization,
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        Page<UserType> userType = searchUserTypeUseCase.findAll(page,size);

        List<UserTypeResponseDTO> userTypes = userType.stream()
                .map(userTypeMapper::toResponse)
                .toList();

        return ResponseEntity.ok(userTypes);

    }
}
