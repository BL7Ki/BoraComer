package pos.java.bora_comer.infra.delivery.reserve;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.CreateReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.doc.CreateReserveControllerDocs;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;

@RestController
@RequestMapping("/reserves")
public class CreateReserveController implements CreateReserveControllerDocs {

    private final ReserveMapper reserveMapper;
    private final CreateReserveUseCase createReserveUseCase;

    public CreateReserveController(ReserveMapper reserveMapper, CreateReserveUseCase createReserveUseCase) {
        this.reserveMapper = reserveMapper;
        this.createReserveUseCase = createReserveUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ReserveResponseDTO> create(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ReserveRequestDTO reserveRequestDTO) {
        var reserveDomain = reserveMapper.toDomain(reserveRequestDTO);
        Reserve createdReserve = createReserveUseCase.execute(reserveDomain);
        ReserveResponseDTO responseDTO = reserveMapper.toResponseDTO(createdReserve);
        URI location = URI.create("/reserves/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

}
