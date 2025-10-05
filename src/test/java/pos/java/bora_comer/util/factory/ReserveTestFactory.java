package pos.java.bora_comer.util.factory;

import java.time.LocalDateTime;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;

public class ReserveTestFactory {
    
    // Construtor privado para impedir instanciação
    private ReserveTestFactory() {
        // impede instanciação
    }
    
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    
    public static Reserve createDefault() {
        return Reserve.create(
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
    }

    public static Reserve createDefaultWithId() {
        return Reserve.create(
                10L,
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
    }

    public static ReserveResponseDTO createResponseDTOWithId() {
        return new ReserveResponseDTO(
                10L,
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
    }

    public static ReserveRequestDTO createRequestDTOWithId() {
        return new ReserveRequestDTO(
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
    }

    public static ReserveUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new ReserveUpdateRequestDTO(
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
    }

    public static Reserve createCustom(Long id, LocalDateTime dateTime, int quantity, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return Reserve.create(
                id,
                dateTime,
                quantity,
                restaurantId,
                userId,
                lastModifiedDate
        );
    }
}

