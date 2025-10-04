package pos.java.bora_comer.infra.delivery.reserve;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.UpdateReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.doc.UpdateReserveControllerDocs;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;

@RestController
@RequestMapping("/reserves")
public class UpdateReserveController implements UpdateReserveControllerDocs {

    private final ReserveMapper reserveMapper;
    private final UpdateReserveUseCase updateReserveUseCase;

    public UpdateReserveController(ReserveMapper reserveMapper, UpdateReserveUseCase updateReserveUseCase) {
        this.reserveMapper = reserveMapper;
        this.updateReserveUseCase = updateReserveUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody ReserveUpdateRequestDTO updateRequestDTO) {
        // Busca o pedido existente para preservar o restaurantId e userId
        Reserve existingReserve = updateReserveUseCase.findById(id);
        var reserveDomain = reserveMapper.toDomain(updateRequestDTO, id, existingReserve.getRestaurantId(), existingReserve.getUserId());

        Reserve updatedReserve = updateReserveUseCase.execute(reserveDomain);
        ReserveResponseDTO responseDTO = reserveMapper.toResponseDTO(updatedReserve);
        return ResponseEntity.ok(responseDTO);
    }

}
