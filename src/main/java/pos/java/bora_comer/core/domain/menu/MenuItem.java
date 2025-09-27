package pos.java.bora_comer.core.domain.menu;

import java.math.BigDecimal;

public class MenuItem {

    private Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final boolean delivery;
    private final String imagePath;
    private final Long restaurantId;

    private MenuItem(Long id, String name, String description, BigDecimal price, boolean delivery, String imagePath, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.delivery = delivery;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;   
    }

    // Factory method without ID (for creation)
    public static MenuItem create(String name, String description, BigDecimal price, boolean delivery, String imagePath, Long restaurantId) {
        return new MenuItem(null, name, description, price, delivery, imagePath, restaurantId);
    }

    // Factory method with ID (for updates)
    public static MenuItem create(Long id, String name, String description, BigDecimal price, boolean delivery, String imagePath,  Long restaurantId) {
        return new MenuItem(id, name, description, price, delivery, imagePath, restaurantId);
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public boolean isDelivery() { return delivery; }
    public String getImagePath() { return imagePath; }
    public Long getRestaurantId() { return restaurantId; }
}
