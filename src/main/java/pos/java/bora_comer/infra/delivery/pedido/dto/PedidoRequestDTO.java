package pos.java.bora_comer.infra.delivery.pedido.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PedidoRequestDTO(
        @JsonProperty("data_hora") LocalDateTime dateTimeOrder, 
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurant_id") Long restaurantId,
        @JsonProperty("usuario_id") Long userId,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate
) {}
