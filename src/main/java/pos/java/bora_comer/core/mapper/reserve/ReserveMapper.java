package pos.java.bora_comer.core.mapper.reserve;
import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

public interface ReserveMapper {

    Reserve toDomain(ReserveRequestDTO reserveRequestDTO);
    
    ReserveEntity toEntity(Reserve order);

    Reserve toDomain(ReserveEntity orderEntity);

    ReserveResponseDTO toResponseDTO(Reserve order);

    Reserve toDomain(ReserveUpdateRequestDTO OrderUpdateRequestDTO, Long id, Long restaurantId, Long userId);
}
