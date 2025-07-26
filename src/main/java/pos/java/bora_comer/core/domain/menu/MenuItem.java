package pos.java.bora_comer.core.domain.menu;

import java.math.BigDecimal;

public class MenuItem {

    private Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final boolean inPlaceOnly;
    private final String imagePath;

    private MenuItem(Long id, String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inPlaceOnly = inPlaceOnly;
        this.imagePath = imagePath;
    }

    public static MenuItem create(String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath) {
        return new MenuItem(null, name, description, price, inPlaceOnly, imagePath);
    }

    public static MenuItem createWithId(Long id, String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath) {
        return new MenuItem(id, name, description, price, inPlaceOnly, imagePath);
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public boolean isInPlaceOnly() { return inPlaceOnly; }
    public String getImagePath() { return imagePath; }
}
