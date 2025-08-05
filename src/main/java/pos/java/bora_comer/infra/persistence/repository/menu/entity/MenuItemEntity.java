package pos.java.bora_comer.infra.persistence.repository.menu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_menu_items")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "descricao", length = 255)
    private String description;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Column(name = "so_no_local", nullable = false)
    private boolean inPlaceOnly;

    @Column(name = "imagem_caminho", length = 255)
    private String imagePath;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime lastModifiedDate;

    // Relacionamento com o restaurante
    @Column(name = "restaurante_id", nullable = false)
    private Long restaurantId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    } 

    public String getDescription() {
        return description;
    }   

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isInPlaceOnly() {
        return inPlaceOnly;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    // Construtor padrão para JPA
    public MenuItemEntity() {   
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Factory method
    public static MenuItemEntity create(String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath, Long restaurantId) {
        return new MenuItemEntity(name, description, price, inPlaceOnly, imagePath, restaurantId);
    }       

    // Construtor privado completo
    private MenuItemEntity(String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath, Long restaurantId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.inPlaceOnly = inPlaceOnly;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Atualizadores controlados
    public void updateName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrice(BigDecimal price) {
        this.price = price;
    }
    
    public void updateInPlaceOnly(boolean inPlaceOnly) {
        this.inPlaceOnly = inPlaceOnly;
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updateRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    // Atualiza a data de modificação
    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
