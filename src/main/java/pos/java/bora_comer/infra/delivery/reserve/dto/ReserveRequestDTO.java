package pos.java.bora_comer.infra.delivery.reserve.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReserveRequestDTO(
        @JsonProperty("data_hora") LocalDateTime dateTimeReserve, 
        @JsonProperty("quantidade") int quantity,
        @JsonProperty("restaurante_id") Long restaurantId,
        @JsonProperty("usuario_id") Long userId,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate
) {}
