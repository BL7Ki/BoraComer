package pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedidos_items")
public class PedidoItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o Pedido
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

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
    
    public Long getPedidoId() {
        return pedidoId;
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
    public PedidoItemEntity() {   
        this.lastModifiedDate = LocalDateTime.now();
    }
    
    // Factory method
    public static PedidoItemEntity create(Long pedidoId, Long menuItemId, Integer quantity) {
        return new PedidoItemEntity(pedidoId, menuItemId, quantity);
    }       

    // Construtor privado completo
    private PedidoItemEntity(Long pedidoId, Long menuItemId, Integer quantity) {
        this.pedidoId = pedidoId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Atualizadores controlados

    public void updatePedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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
