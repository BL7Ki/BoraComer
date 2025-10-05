package pos.java.bora_comer.infra.persistence.repository.order.entity;

import jakarta.persistence.*;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_pedidos")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dateTimeOrder;

    @Column(name = "delivery", nullable = false)
    private boolean delivery;

    @Column(name = "restaurante_id", nullable = false)
    private Long restaurantId;

    @Column(name = "usuario_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatusEnum status;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;

    // Construtor padrão para JPA
    public OrderEntity() {
    }

    public static OrderEntity create(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, OrderStatusEnum status, BigDecimal valorTotal, LocalDateTime lastModifiedDate) {
        OrderEntity entity = new OrderEntity();
        entity.id = id;
        entity.dateTimeOrder = dateTimeOrder;
        entity.delivery = delivery;
        entity.restaurantId = restaurantId;
        entity.userId = userId;
        entity.status = status;
        entity.valorTotal = valorTotal;
        entity.lastModifiedDate = lastModifiedDate;
        return entity;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public LocalDateTime getDateTimeOrder() { return dateTimeOrder; }
    public boolean isDelivery() { return delivery; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public OrderStatusEnum getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }
}