package pos.java.bora_comer.core.mapper.restaurant;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

public interface RestaurantMapper {

    Restaurant toDomain(RestaurantRequestDTO restaurantRequestDTO);

    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toDomain(RestaurantEntity restaurantEntity);

    RestaurantResponseDTO toResponseDTO(Restaurant restaurant);

    Restaurant toDomain(RestaurantUpdateRequestDTO restaurantUpdateRequestDTO, Long id);
}
