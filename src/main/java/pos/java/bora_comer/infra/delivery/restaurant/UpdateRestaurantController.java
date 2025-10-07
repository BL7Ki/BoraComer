package pos.java.bora_comer.infra.delivery.restaurant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.doc.UpdateRestaurantControllerDocs;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;

@RestController
@RequestMapping("/restaurants")
public class UpdateRestaurantController implements UpdateRestaurantControllerDocs {

    private final RestaurantMapper restaurantMapper;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public UpdateRestaurantController(RestaurantMapper restaurantMapper, UpdateRestaurantUseCase updateRestaurantUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> update(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id,
            @RequestBody RestaurantUpdateRequestDTO updateRequestDTO) {
        // Busca o restaurante existente para preservar o ownerId
        Restaurant existingRestaurant = updateRestaurantUseCase.findById(id);
        var restaurantDomain = restaurantMapper.toDomain(updateRequestDTO, id, existingRestaurant.getOwnerId());

        Restaurant updatedRestaurant = updateRestaurantUseCase.execute(restaurantDomain);
        RestaurantResponseDTO responseDTO = restaurantMapper.toResponseDTO(updatedRestaurant);
        return ResponseEntity.ok(responseDTO);
    }

}
