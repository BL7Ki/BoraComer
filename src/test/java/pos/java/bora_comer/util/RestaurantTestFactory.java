package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;

public class RestaurantTestFactory {

    private RestaurantTestFactory() {
        // impede instanciação
    }

    public static Restaurant createDefault() {
        return Restaurant.create(
                1L,
                "Default Name",
                "Default Address",
                "Default Cuisine",
                "09:00 - 18:00",
                1L
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

