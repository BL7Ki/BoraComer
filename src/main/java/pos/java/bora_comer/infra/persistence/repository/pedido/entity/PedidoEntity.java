package pos.java.bora_comer.infra.persistence.repository.pedido.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedidos")
public class PedidoEntity {

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

    // Getters and Setters
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

    // Construtor padrão para JPA
    public PedidoEntity() {   
        this.lastModifiedDate = LocalDateTime.now();
    }
    
    // Factory method
    public static PedidoEntity create(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId ) {
        return new PedidoEntity(dateTimeOrder, delivery, restaurantId, userId);
    }       

    // Construtor privado completo
    private PedidoEntity(LocalDateTime dateTimeOrder, boolean delivery, Long restaurantId, Long userId) {
        this.dateTimeOrder = dateTimeOrder;
        this.delivery = delivery;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Atualizadores controlados
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
}
