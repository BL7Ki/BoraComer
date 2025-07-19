package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;

public class RestaurantTestFactory {

    private RestaurantTestFactory() {
        // impede instanciação
    }

    public static Restaurant createDefault() {
        return Restaurant.create(
                "Restaurante Japa",
                "Rua B, 456",
                "Japonesa",
                "11:00 - 23:00",
                55L
        );
    }

    public static Restaurant createDefaultWithId() {
        return Restaurant.create(
                10L,
                "Restaurante Japa",
                "Rua B, 456",
                "Japonesa",
                "11:00 - 23:00",
                55L
        );
    }

    public static Restaurant createCustom(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        return Restaurant.create(
                id,
                name,
                address,
                cuisineType,
                openingHours,
                ownerId
        );
    }
}

