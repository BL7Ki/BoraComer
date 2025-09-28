package pos.java.bora_comer.infra.delivery.pedidoItem.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PedidoItemUpdateRequestDTO(
        @JsonProperty("pedido_id") Long pedidoId,
        @JsonProperty("menu_item_id") Long menuItemId,
        @JsonProperty("quantidade") Integer quantity,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate
) {
}
