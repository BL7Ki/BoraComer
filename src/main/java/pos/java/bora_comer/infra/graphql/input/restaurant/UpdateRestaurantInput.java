package pos.java.bora_comer.infra.graphql.input.restaurant;

public record UpdateRestaurantInput(
        String name,
        String address,
        String cuisineType,
        String openingHours,
        Long ownerId
) {}