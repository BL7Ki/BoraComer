package pos.java.bora_comer.infra.persistence.repository.orderItem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedidos_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o Pedido
    @Column(name = "pedido_id", nullable = false)
    private Long orderId;

    // Relacionamento com o Item de Menu
    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;    

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    // Construtor padrão para JPA
    public OrderItemEntity() {   
        this.lastModifiedDate = LocalDateTime.now();
    }
    
    // Factory method
    public static OrderItemEntity create(Long orderId, Long menuItemId, Integer quantity) {
        return new OrderItemEntity(orderId, menuItemId, quantity);
    }       

    // Construtor privado completo
    private OrderItemEntity(Long orderId, Long menuItemId, Integer quantity) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Atualizadores controlados

    public void updateOrderId(Long orderId) {
        this.orderId = orderId;
    }
        
    public void updateMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
