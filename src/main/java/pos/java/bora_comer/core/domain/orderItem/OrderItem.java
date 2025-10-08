package pos.java.bora_comer.core.domain.orderItem;
import java.time.LocalDateTime;

public class OrderItem {

    private Long id;
    private final Long orderId;
    private final Long menuItemId;
    private final int quantity;
    private final LocalDateTime lastModifiedDate; 

    private OrderItem(Long id, Long orderId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Factory method without ID (for creation)
    public static OrderItem create(Long orderId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return new OrderItem(null, orderId, menuItemId, quantity, lastModifiedDate);
    }

    // Factory method with ID (for updates)
    public static OrderItem create(Long id, Long orderId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return new OrderItem( id, orderId, menuItemId, quantity, lastModifiedDate);
    }

    // Getters
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public Long getMenuItemId() { return menuItemId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
}