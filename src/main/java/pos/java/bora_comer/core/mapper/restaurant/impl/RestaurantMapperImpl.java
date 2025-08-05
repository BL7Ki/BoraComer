package pos.java.bora_comer.core.mapper.restaurant.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant toDomain(RestaurantRequestDTO restaurantRequestDTO) {
        if (restaurantRequestDTO == null) {
            throw new RestaurantDomainException("RestaurantRequestDTO não pode ser nulo");
        }

        return Restaurant.create(
                restaurantRequestDTO.name(),
                restaurantRequestDTO.address(),
                restaurantRequestDTO.cuisineType(),
                restaurantRequestDTO.openingHours(),
                restaurantRequestDTO.ownerId()
        );
    }

    @Override
    public RestaurantEntity toEntity(Restaurant restaurant) {
        if (restaurant == null) {
            throw new RestaurantDomainException("Restaurant não pode ser nulo");
        }

        return RestaurantEntity.create(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getOwnerId()
        );
    }

    @Override
    public Restaurant toDomain(RestaurantEntity restaurantEntity) {
        if (restaurantEntity == null) {
            throw new RestaurantDomainException("RestaurantEntity não pode ser nulo");
        }

        return Restaurant.create(
                restaurantEntity.getId(),
                restaurantEntity.getName(),
                restaurantEntity.getAddress(),
                restaurantEntity.getCuisineType(),
                restaurantEntity.getOpeningHours(),
                restaurantEntity.getOwnerId()
        );
    }

    @Override
    public RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        if (restaurant == null) {
            throw new RestaurantDomainException("Restaurant não pode ser nulo");
        }

        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getOwnerId()
        );
    }

    @Override
    public Restaurant toDomain(RestaurantUpdateRequestDTO restaurantUpdateRequestDTO, Long id, Long ownerId) {
        if (restaurantUpdateRequestDTO == null) {
            throw new RestaurantDomainException("RestaurantUpdateRequestDTO não pode ser nulo");
        }

        return Restaurant.create(
                id,
                restaurantUpdateRequestDTO.name(),
                restaurantUpdateRequestDTO.address(),
                restaurantUpdateRequestDTO.cuisineType(),
                restaurantUpdateRequestDTO.openingHours(),
                ownerId // preserva o ownerId que vem do parâmetro
        );
    }
}
