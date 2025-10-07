package pos.java.bora_comer.core.domain.order;
import java.time.LocalDateTime;

public class Order {

    private Long id;
    private final LocalDateTime dateTimeOrder;
    private final boolean delivery;
    private final Long restaurantId;
    private final Long userId;
    private final LocalDateTime lastModifiedDate;
    private final OrderStatus status; // NOVO CAMPO

    // Construtor privado para forçar o uso dos métodos de fábrica ou do Builder
    private Order(Long id,
                  LocalDateTime dateTimeOrder,
                  boolean delivery,
                  Long restaurantId,
                  Long userId,
                  LocalDateTime lastModifiedDate,
                  OrderStatus status) { // NOVO PARAMETRO
        this.id = id;
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = lastModifiedDate;
        this.status = status;
    }

    // --- Métodos de Fábrica ---

    // Factory method without ID (for creation - assume status PENDING)
    public static Order create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        // Pedidos novos sempre iniciam como PENDING
        return new Order(null, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate, OrderStatus.PENDING);
    }

    // Factory method with ID (for updates/reconstruction from persistence)
    public static Order create(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate, OrderStatus status) {
        return new Order(id, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate, status);
    }

    // --- Regras de Negócio ---

    // Mais pra frente colocar outras regras de negocio
    public Order finalizer() {
        if (this.status == OrderStatus.FINALIZED) {
            return this;
        }

        // Retorna uma nova instância imutável com o status FINALIZED e a nova data
        return this.toBuilder()
                .status(OrderStatus.FINALIZED)
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    // --- Builder para Atualização ---

    public OrderBuilder toBuilder() {
        return new OrderBuilder()
                .id(this.id)
                .dateTimeOrder(this.dateTimeOrder)
                .delivery(this.delivery)
                .restaurantId(this.restaurantId)
                .userId(this.userId)
                .lastModifiedDate(this.lastModifiedDate)
                .status(this.status);
    }

    // --- Getters ---
    public Long getId() { return id; }
    public LocalDateTime getDateTimeOrder() { return dateTimeOrder; }
    public boolean isDelivery() { return delivery; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public OrderStatus getStatus() { return status; } // <<< NOVO GETTER

    // --- Classe Builder Interna ---

    public static class OrderBuilder {
        private Long id;
        private LocalDateTime dateTimeOrder;
        private boolean delivery;
        private Long restaurantId;
        private Long userId;
        private LocalDateTime lastModifiedDate;
        private OrderStatus status;

        public OrderBuilder id(Long id) { this.id = id; return this; }
        public OrderBuilder dateTimeOrder(LocalDateTime dateTimeOrder) { this.dateTimeOrder = dateTimeOrder; return this; }
        public OrderBuilder delivery(boolean delivery) { this.delivery = delivery; return this; }
        public OrderBuilder restaurantId(Long restaurantId) { this.restaurantId = restaurantId; return this; }
        public OrderBuilder userId(Long userId) { this.userId = userId; return this; }
        public OrderBuilder lastModifiedDate(LocalDateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; return this; }
        public OrderBuilder status(OrderStatus status) { this.status = status; return this; }

        public Order build() {
            return new Order(id, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate, status);
        }
    }
}