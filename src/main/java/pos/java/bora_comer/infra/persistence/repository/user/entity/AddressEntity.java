package pos.java.bora_comer.infra.persistence.repository.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AddressEntity {

    @Column(name = "rua", length = 100)
    private String street;

    @Column(name = "bairro", length = 100)
    private String neighborhood;

    @Column(name = "cidade", length = 50)
    private String city;

    @Column(name = "estado", length = 100)
    private String state;

    @Column(name = "cep", length = 10)
    private String zipCode;

    // Construtor padrão necessário para a JPA
    protected AddressEntity() {
    }

    public static AddressEntity create(String street, String neighborhood, String city, String state, String zipCode) {
        return new AddressEntity(street, neighborhood, city, state, zipCode);
    }

    private AddressEntity(String street, String neighborhood, String city, String state, String zipCode) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    // Getters and Setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

