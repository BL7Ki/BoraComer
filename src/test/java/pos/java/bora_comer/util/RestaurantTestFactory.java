package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;

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

    public static RestaurantResponseDTO createResponseDTOWithId() {
        return new RestaurantResponseDTO(
                10L,
                "Restaurante Japa",
                "Rua B, 456",
                "Japonesa",
                "11:00 - 23:00",
                55L
        );
    }

    public static RestaurantRequestDTO createRequestDTOWithId() {
        return new RestaurantRequestDTO(
                "Restaurante Japa",
                "Rua B, 456",
                "Japonesa",
                "11:00 - 23:00",
                55L
        );
    }

    public static RestaurantUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new RestaurantUpdateRequestDTO(
                "Updated Name",
                "Updated Address",
                "Updated Cuisine",
                "09:00 - 21:00",
                2L
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

