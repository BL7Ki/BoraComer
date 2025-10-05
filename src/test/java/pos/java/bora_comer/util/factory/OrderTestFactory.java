package pos.java.bora_comer.util.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

public class OrderTestFactory {

    private OrderTestFactory() {
    }

    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    private static final OrderStatusEnum defaultStatus = OrderStatusEnum.CREATED;
    private static final BigDecimal defaultValorTotal = BigDecimal.ZERO;

    public static Order createDefault() {
        return Order.create(
                dateTime,
                true,
                1L,
                1L
        );
    }

    public static Order createDefaultWithId() {
        return Order.create(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime,
                defaultStatus,
                defaultValorTotal
        );
    }
    public static Order createDefaultWithId(Long id, OrderStatusEnum status) {
        return Order.create(
                id,
                dateTime,
                true,
                1L,
                1L,
                dateTime,
                status,
                defaultValorTotal
        );
    }

    public static OrderResponseDTO createResponseDTOWithId() {
        return new OrderResponseDTO(
                10L,
                dateTime,
                true,
                1L,
                1L,
                defaultStatus.name(),
                dateTime
        );
    }

    public static OrderRequestDTO createRequestDTO() {
        return new OrderRequestDTO(
                dateTime,
                true,
                1L,
                1L
        );
    }

    public static OrderUpdateRequestDTO createUpdateRequestDTO() {
        return new OrderUpdateRequestDTO(
                dateTime.plusHours(1),
                false,
                1L,
                1L,
                OrderStatusEnum.IN_PROGRESS.name()
        );
    }

    public static Order createCustom(Long id, LocalDateTime dateTime, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate, OrderStatusEnum status, BigDecimal valorTotal) {
        return Order.create(
                id,
                dateTime,
                delivery,
                restaurantId,
                userId,
                lastModifiedDate,
                status,
                valorTotal
        );
    }
}