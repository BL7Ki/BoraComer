package pos.java.bora_comer.core.domain;

public class Address {
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

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
