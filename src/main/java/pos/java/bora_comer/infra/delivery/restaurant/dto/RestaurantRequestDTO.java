package pos.java.bora_comer.infra.delivery.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RestaurantRequestDTO(
        @JsonProperty("nome") String name,
        @JsonProperty("endereco") String address,
        @JsonProperty("tipo_cozinha") String cuisineType,
        @JsonProperty("horario_funcionamento") String openingHours,
        @JsonProperty("dono_id") Long ownerId
) {
}
