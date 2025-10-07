package pos.java.bora_comer.util.factory;

import java.time.LocalDateTime;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

public class OrderTestFactory {
    
    // Construtor privado para impedir instanciação
    private OrderTestFactory() {
        // impede instanciação
    }
    
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    
    public static Order createDefault() {
        return Order.create(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static Order createDefaultWithId() {
        return Order.create(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static OrderResponseDTO createResponseDTOWithId() {
        return new OrderResponseDTO(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static OrderRequestDTO createRequestDTOWithId() {
        return new OrderRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static OrderUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new OrderUpdateRequestDTO(
                dateTime,
                false,
                1L,
                1L,
                dateTime
        );
    }

    public static Order createCustom(Long id, LocalDateTime dateTime, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return Order.create(
                id,
                dateTime,
                delivery,
                restaurantId,
                userId,
                lastModifiedDate
        );
    }
}

