package pos.java.bora_comer.core.domain.reserve;
import java.time.LocalDateTime;

public class Reserve {

    private Long id;
    private final LocalDateTime dateTimeReserve; 
    private final int quantity;
    private final Long restaurantId;
    private final Long userId;
    private final LocalDateTime lastModifiedDate; 

    private Reserve(Long id, LocalDateTime dateTimeReserve, int quantity, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.dateTimeReserve = dateTimeReserve;
        this.quantity = quantity;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Factory method without ID (for creation)
    public static Reserve create(LocalDateTime dateTimeReserve, int quantity, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return new Reserve(null, dateTimeReserve, quantity, restaurantId, userId, lastModifiedDate);
    }

    // Factory method with ID (for updates)
    public static Reserve create(Long id, LocalDateTime dateTimeReserve, int quantity, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return new Reserve(id, dateTimeReserve, quantity, restaurantId, userId, lastModifiedDate);
    }

    // Getters
    public Long getId() { return id; }
    public LocalDateTime getDateTimeReserve() { return dateTimeReserve; }
    public int getQuantity() { return quantity; }
    public Long getRestaurantId() { return restaurantId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }

  
}