package pos.java.bora_comer.infra.persistence.repository.reserve.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reservas")
public class ReserveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dateTimeReserve;
    
    @Column(name = "quantidade", nullable = false)
    private int quantity;

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
    
    public LocalDateTime getDateTimeReserve() {
        return dateTimeReserve;
    }
        
    public int getQuantity() {
        return quantity;
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
    public ReserveEntity() {   
        this.lastModifiedDate = LocalDateTime.now();
    }
    
    // Factory method
    public static ReserveEntity create(LocalDateTime dateTimeReserve, int quantity, Long restaurantId, Long userId ) {
        return new ReserveEntity(dateTimeReserve, quantity, restaurantId, userId);
    }       

    // Construtor privado completo
    private ReserveEntity(LocalDateTime dateTimeReserve, int quantity, Long restaurantId, Long userId) {
        this.dateTimeReserve = dateTimeReserve;
        this.quantity = quantity;
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
    
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateDateTimeReserve(LocalDateTime dateTimeReserve) {
        this.dateTimeReserve = dateTimeReserve;
    }

    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
