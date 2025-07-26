package pos.java.bora_comer.infra.persistence.repository.menu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_menu_items")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "in_place_only", nullable = false)
    private boolean inPlaceOnly;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    // Getters and Setters
}
