package pos.java.bora_comer.infra.delivery.userType;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.domain.UserType;
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

    @GetMapping
    public ResponseEntity<List<UserTypeResponseDTO>> findAll(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        Page<UserType> userType = searchUserTypeUseCase.findAll(page,size);

        List<UserTypeResponseDTO> userTypes = userType.stream()
                .map(userTypeMapper::toResponse)
                .toList();

        return ResponseEntity.ok(userTypes);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> findById(@PathVariable Long id) {

        UserType userType = searchUserTypeUseCase.findById(id);
        UserTypeResponseDTO userTypeResponseDTO = userTypeMapper.toResponse(userType);
        return ResponseEntity.ok(userTypeResponseDTO);
    }
}
