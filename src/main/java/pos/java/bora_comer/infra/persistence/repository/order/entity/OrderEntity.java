package pos.java.bora_comer.infra.persistence.repository.order.entity;

import jakarta.persistence.*;
import pos.java.bora_comer.core.domain.order.OrderStatus;
import java.time.LocalDateTime;

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

    // Relacionamento com o restaurante
    @Column(name = "restaurante_id", nullable = false)
    private Long restaurantId;

    // Relacionamento com o usuário
    @Column(name = "usuario_id", nullable = false)
    private Long userId;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private OrderStatus status;


    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTimeOrder() {
        return dateTimeOrder;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // --- Construtores ---

    // Construtor padrão para JPA
    public OrderEntity() {
    }

    // Factory method (Atualizado para incluir status)
    public static OrderEntity create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, OrderStatus status) {
        return new OrderEntity(dateTimeOrder, delivery, restaurantId, userId, status);
    }

    // Construtor privado completo (Atualizado para incluir status)
    private OrderEntity(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, OrderStatus status) {
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = LocalDateTime.now();
        this.status = status; // Inicializa o status
    }

    // --- Atualizadores Controlados ---

    public void updateRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void updateUserId(Long userId) {
        this.userId = userId;
    }

    public void updateDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public void updateDateTimeOrder(LocalDateTime dateTimeOrder) {
        this.dateTimeOrder = dateTimeOrder;
    }

    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
