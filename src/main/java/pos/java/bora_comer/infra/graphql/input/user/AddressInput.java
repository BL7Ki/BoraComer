package pos.java.bora_comer.infra.graphql.input.user;

public record AddressInput(
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {}