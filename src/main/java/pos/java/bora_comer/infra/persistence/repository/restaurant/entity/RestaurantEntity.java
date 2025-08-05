package pos.java.bora_comer.infra.persistence.repository.restaurant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_restaurantes")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "endereco", nullable = false, length = 255)
    private String address;

    @Column(name = "tipo_cozinha", nullable = false, length = 50)
    private String cuisineType;

    @Column(name = "horario_funcionamento", nullable = false, length = 100)
    private String openingHours;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;

    // Relacionamento com o dono do restaurante
    @Column(name = "dono_id", nullable = false)
    private Long ownerId;

    // Construtor padrão para JPA
    public RestaurantEntity() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Factory method
    public static RestaurantEntity create(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        return new RestaurantEntity(name, address, cuisineType, openingHours, ownerId);
    }

    // Construtor privado completo
    private RestaurantEntity(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    // Atualizadores controlados
    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void updateOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void updateOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
