package pos.java.bora_comer.infra.delivery.order.dto;

import java.math.BigDecimal;

public record CreateOrderDTO(String clienteId, BigDecimal valorTotal) {
}
