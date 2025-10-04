package pos.java.bora_comer.core.domain.user;

public class Address {

    private final String street;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;

    public static Address create(String street, String neighborhood, String city, String state, String zipCode) {
        return new Address(street, neighborhood, city, state, zipCode);
    }

    private Address(String street, String neighborhood, String city, String state, String zipCode) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    // Getters
    public String getStreet() {
        return street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}