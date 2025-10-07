package pos.java.bora_comer.infra.delivery.orderItem.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderItemRequestDTO(
        @JsonProperty("pedido_id") Long orderId,
        @JsonProperty("menu_item_id") Long menuItemId,
        @JsonProperty("quantidade") Integer quantity,
        @JsonProperty("data_alteracao") LocalDateTime lastModifiedDate
) {}
