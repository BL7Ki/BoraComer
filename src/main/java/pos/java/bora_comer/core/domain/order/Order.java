package pos.java.bora_comer.core.domain.order;

import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private Long id;
    private final LocalDateTime dateTimeOrder;
    private final boolean delivery;
    private final Long restaurantId;
    private final Long userId;
    private final LocalDateTime lastModifiedDate;
    private final OrderStatusEnum status;
    private final BigDecimal valorTotal;

    private Order(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate, OrderStatusEnum status, BigDecimal valorTotal) {
        this.id = id;
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = lastModifiedDate;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public static Order create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate, OrderStatusEnum status, BigDecimal valorTotal) {
        return new Order(null, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate, status, valorTotal);
    }

    public static Order create(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate, OrderStatusEnum status, BigDecimal valorTotal) {
        return new Order(id, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate, status, valorTotal);
    }

    public static Order create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId) {
        return new Order(null, dateTimeOrder, delivery, restaurantId, userId, null, OrderStatusEnum.CREATED, BigDecimal.ZERO);
    }

    public Order updateStatus(OrderStatusEnum newStatus) {
        return new Order(
                this.id,
                this.dateTimeOrder,
                this.delivery,
                this.restaurantId,
                this.userId,
                LocalDateTime.now(),
                newStatus,
                this.valorTotal
        );
    }

    public Order applyUpdate(OrderUpdateRequestDTO updateDTO) {

        OrderStatusEnum newStatus = updateDTO.status() != null
                ? OrderStatusEnum.valueOf(updateDTO.status())
                : this.status;

        return new Order(
                this.id,
                updateDTO.dateTimeOrder() != null ? updateDTO.dateTimeOrder() : this.dateTimeOrder,
                updateDTO.delivery() != null ? updateDTO.delivery() : this.delivery,
                updateDTO.restaurantId() != null ? updateDTO.restaurantId() : this.restaurantId,
                updateDTO.userId() != null ? updateDTO.userId() : this.userId,
                LocalDateTime.now(),
                newStatus,
                this.valorTotal
        );
    }

    public Long getId() { return id; }
    public LocalDateTime getDateTimeOrder() { return dateTimeOrder; }
    public boolean isDelivery() { return delivery; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public OrderStatusEnum getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }
}