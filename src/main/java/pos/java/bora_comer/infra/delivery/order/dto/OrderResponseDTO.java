package pos.java.bora_comer.infra.delivery.order.dto;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import pos.java.bora_comer.core.domain.order.OrderStatus;

public record OrderResponseDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("data_hora") LocalDateTime dateTimeOrder,
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurante_id") Long restaurantId,
        @JsonProperty("usuario_id") Long userId,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate,
        @JsonProperty("status") OrderStatus status
) {
}
