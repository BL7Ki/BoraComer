package pos.java.bora_comer.util.factory;

import java.time.LocalDateTime;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;

public class OrderItemTestFactory {
    
    // Construtor privado para impedir instanciação
    private OrderItemTestFactory() {
        // impede instanciação
    }
    
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    
    public static OrderItem createDefault() {
        return OrderItem.create(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static OrderItem createDefaultWithId() {
        return OrderItem.create(
                10L,
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static OrderItemResponseDTO createResponseDTOWithId() {
        return new OrderItemResponseDTO(
                10L,
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static OrderItemRequestDTO createRequestDTOWithId() {
        return new OrderItemRequestDTO(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static OrderItemUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new OrderItemUpdateRequestDTO(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static OrderItem createCustom(Long id, Long orderId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return OrderItem.create(
                id,
                orderId,
                menuItemId,
                quantity,
                lastModifiedDate
        );
    }
}

