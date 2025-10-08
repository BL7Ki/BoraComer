package pos.java.bora_comer.infra.delivery.order.dto;

import java.time.LocalDateTime;
import pos.java.bora_comer.core.domain.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderUpdateRequestDTO(
        @JsonProperty("data_hora") LocalDateTime dateTimeOrder, 
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurant_id") Long restaurantId,
        @JsonProperty("usuario_id") Long userId,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate,
        @JsonProperty("status") OrderStatus status
) {
}
