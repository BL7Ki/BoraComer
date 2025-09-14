package pos.java.bora_comer.core.domain.pedido;
import java.time.LocalDateTime;

public class Pedido {

    private Long id;
    private final LocalDateTime dateTimeOrder; 
    private final boolean delivery;
    private final Long restaurantId;
    private final Long userId;
    private final LocalDateTime lastModifiedDate; 

    private Pedido(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Factory method without ID (for creation)
    public static Pedido create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return new Pedido(null, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate);
    }

    // Factory method with ID (for updates)
    public static Pedido create(Long id, LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return new Pedido(id, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate);
    }

    // Getters
    public Long getId() { return id; }
    public LocalDateTime getDateTimeOrder() { return dateTimeOrder; }
    public boolean isDelivery() { return delivery; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
}