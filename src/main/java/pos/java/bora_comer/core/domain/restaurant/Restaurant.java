package pos.java.bora_comer.core.domain.restaurant;

public class Restaurant {

    private Long id;
    private final String name;
    private final String address;
    private final String cuisineType;
    private final String openingHours;
    private final Long ownerId;

    private Restaurant(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
    }

    // --- Métodos de Fábrica (Criação) ---

    // Factory method without ID (para criação a partir do DTO de Input)
    public static Restaurant create(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        return new Restaurant(null, name, address, cuisineType, openingHours, ownerId);
    }

    // Factory method with ID (para reconstrução a partir da persistência)
    public static Restaurant create(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        return new Restaurant(id, name, address, cuisineType, openingHours, ownerId);
    }

    // --- Builder para Atualização ---
    public RestaurantBuilder toBuilder() {
        return new RestaurantBuilder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .cuisineType(this.cuisineType)
                .openingHours(this.openingHours)
                .ownerId(this.ownerId);
    }

    // --- Getters ---

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCuisineType() { return cuisineType; }
    public String getOpeningHours() { return openingHours; }
    public Long getOwnerId() { return ownerId; }

    public Restaurant updateOpeningHours(String newOpeningHours) {
        return create(this.id, this.name, this.address, this.cuisineType, newOpeningHours, this.ownerId);
    }

    // --- Classe Builder Interna ---

    public static class RestaurantBuilder {
        private Long id;
        private String name;
        private String address;
        private String cuisineType;
        private String openingHours;
        private Long ownerId;

        public RestaurantBuilder id(Long id) { this.id = id; return this; }
        public RestaurantBuilder name(String name) { this.name = name; return this; }
        public RestaurantBuilder address(String address) { this.address = address; return this; }
        public RestaurantBuilder cuisineType(String cuisineType) { this.cuisineType = cuisineType; return this; }
        public RestaurantBuilder openingHours(String openingHours) { this.openingHours = openingHours; return this; }
        public RestaurantBuilder ownerId(Long ownerId) { this.ownerId = ownerId; return this; }

        public Restaurant build() {
            return new Restaurant(id, name, address, cuisineType, openingHours, ownerId);
        }
    }
}
