package pos.java.bora_comer.core.domain.pedidoItem;
import java.time.LocalDateTime;

public class PedidoItem {

    private Long id;
    private final Long pedidoId;
    private final Long menuItemId;
    private final int quantity;
    private final LocalDateTime lastModifiedDate; 

    private PedidoItem(Long id, Long pedidoId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Factory method without ID (for creation)
    public static PedidoItem create(Long pedidoId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return new PedidoItem(null, pedidoId, menuItemId, quantity, lastModifiedDate);
    }

    // Factory method with ID (for updates)
    public static PedidoItem create(Long id, Long pedidoId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return new PedidoItem( id, pedidoId, menuItemId, quantity, lastModifiedDate);
    }

    // Getters
    public Long getId() { return id; }
    public Long getPedidoId() { return pedidoId; }
    public Long getMenuItemId() { return menuItemId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
}