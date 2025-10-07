package pos.java.bora_comer.infra.delivery.reserve;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.SearchReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.doc.SearchReserveControllerDocs;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/reserves")
public class SearchReserveController implements SearchReserveControllerDocs {

    private final SearchReserveUseCase searchReserveUseCase;
    private final ReserveMapper reserveMapper;

    public SearchReserveController(SearchReserveUseCase searchReserveUseCase, ReserveMapper reserveMapper) {
        this.searchReserveUseCase = searchReserveUseCase;
        this.reserveMapper = reserveMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> findById(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        var reserve = searchReserveUseCase.findById(id);
        return ResponseEntity.ok(reserveMapper.toResponseDTO(reserve));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ReserveResponseDTO>> findAll(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Reserve> reserves = searchReserveUseCase.findAll(page, size);

        List<ReserveResponseDTO> responseList = reserves.stream()
                .map(reserveMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
