package pos.java.bora_comer.infra.graphql.input.restaurant;

public record CreateRestaurantInput(
        String name,
        String address,
        String cuisineType,
        String openingHours,
        Long ownerId
) {}