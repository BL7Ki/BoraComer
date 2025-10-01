package pos.java.bora_comer.infra.persistence.repository.order.entity;

import jakarta.persistence.*;
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

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;

    // Construtor padrão para JPA
    public OrderEntity() {
        this.lastModifiedDate = LocalDateTime.now();
        this.status = "CREATED";
        this.valorTotal = BigDecimal.ZERO;
    }

    // Factory method
    public static OrderEntity create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId ) {
        return new OrderEntity(dateTimeOrder, delivery, restaurantId, userId);
    }

    // Construtor privado completo
    private OrderEntity(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId) {
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.status = "CREATED";
        this.valorTotal = BigDecimal.ZERO;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // --- Getters ---
    public Long getId() { return id; }
    public LocalDateTime getDateTimeOrder() { return dateTimeOrder; }
    public boolean isDelivery() { return delivery; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public String getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }

    // --- Atualizadores Controlados ---

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("O status não pode ser nulo ou vazio.");
        }
        this.status = newStatus;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateDelivery(boolean delivery) {
        this.delivery = delivery;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateUserId(Long userId) {
        this.userId = userId;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateDateTimeOrder(LocalDateTime dateTimeOrder) {
        this.dateTimeOrder = dateTimeOrder;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}