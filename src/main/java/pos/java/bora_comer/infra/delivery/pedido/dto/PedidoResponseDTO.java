package pos.java.bora_comer.infra.delivery.pedido.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PedidoResponseDTO(
        @JsonProperty("id") Long id, 
        @JsonProperty("data_hora") String dateTimeOrder,
        @JsonProperty("delivery") boolean delivery,
        @JsonProperty("restaurant_id") Long restaurantId,
        @JsonProperty("usuario_id") Long usuarioId,
        @JsonProperty("data_alteracao") String dateTimeAlter
        ) {
}
