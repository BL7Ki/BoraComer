package pos.java.bora_comer.infra.delivery.menu.dto;

import java.math.BigDecimal;

public record MenuItemResponseDTO(Long id, String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath) {
}
