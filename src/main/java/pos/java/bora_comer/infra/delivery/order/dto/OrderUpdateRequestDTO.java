package pos.java.bora_comer.infra.delivery.order.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderUpdateRequestDTO(
        @JsonProperty("data_hora") LocalDateTime dateTimeOrder,
        @JsonProperty("delivery") Boolean delivery,
        @JsonProperty("restaurant_id") Long restaurantId,
        @JsonProperty("usuario_id") Long userId,
        @JsonProperty("status") String status
) {
}