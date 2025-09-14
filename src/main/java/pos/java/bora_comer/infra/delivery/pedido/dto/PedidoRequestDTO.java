package pos.java.bora_comer.infra.delivery.pedido.dto;

import java.security.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PedidoRequestDTO(
        @JsonProperty("data_hora") Timestamp dateTimeOrder, 
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurant_id") Long restaurantId,
        @JsonProperty("usuario_id") Long usuarioId,
        @JsonProperty("data_alteracao") Timestamp dateTimeAlter
) {}
