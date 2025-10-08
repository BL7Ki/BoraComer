package pos.java.bora_comer.infra.delivery.order.dto;

import java.time.LocalDateTime;

public record OrderMessageDTO(
        Long id,
        LocalDateTime dateTimeOrder,
        boolean delivery,
        Long restaurantId,
        Long userId
) {}
