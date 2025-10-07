package pos.java.bora_comer.infra.delivery.restaurant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.core.usercase.restaurant.CreateRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.doc.CreateRestaurantControllerDocs;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;

import java.net.URI;

@RestController
@RequestMapping("/restaurants")
public class CreateRestaurantController implements CreateRestaurantControllerDocs {

    private final RestaurantMapper restaurantMapper;
    private final CreateRestaurantUseCase createRestaurantUseCase;

    public CreateRestaurantController(RestaurantMapper restaurantMapper, CreateRestaurantUseCase createRestaurantUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.createRestaurantUseCase = createRestaurantUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RestaurantRequestDTO restaurantRequestDTO
    ) {
        var restaurantDomain = restaurantMapper.toDomain(restaurantRequestDTO);
        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurantDomain);
        RestaurantResponseDTO responseDTO = restaurantMapper.toResponseDTO(createdRestaurant);
        URI location = URI.create("/restaurants/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }
}
