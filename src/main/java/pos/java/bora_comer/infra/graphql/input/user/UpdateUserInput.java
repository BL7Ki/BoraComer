package pos.java.bora_comer.infra.graphql.input.user;

public record UpdateUserInput(
        String name,
        String email,
        AddressInput address
) {}