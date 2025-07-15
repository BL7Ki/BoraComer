package pos.java.bora_comer.core.domain;

public class Restaurant {

    private Long id;
    private final String name;
    private final String address;
    private final String cuisineType;
    private final String openingHours;
    private final Long ownerId;

    // Factory method without ID (for creation)
    public static Restaurant create(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        return new Restaurant(name, address, cuisineType, openingHours, ownerId);
    }

    // Factory method with ID (for updates)
    public static Restaurant create(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        Restaurant restaurant = new Restaurant(name, address, cuisineType, openingHours, ownerId);
        restaurant.id = id;
        return restaurant;
    }

    private Restaurant(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public Restaurant updateOpeningHours(String newOpeningHours) {
        return create(this.id, this.name, this.address, this.cuisineType, newOpeningHours, this.ownerId);
    }
}
