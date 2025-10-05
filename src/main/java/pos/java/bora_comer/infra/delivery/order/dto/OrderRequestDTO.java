package pos.java.bora_comer.infra.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record OrderRequestDTO(
        @JsonProperty("data_hora") LocalDateTime dateTimeOrder,
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurante_id") Long restaurantId, 
        @JsonProperty("usuario_id") Long userId
) {}