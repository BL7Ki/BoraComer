package pos.java.bora_comer.infra.delivery.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.doc.SearchRestaurantControllerDocs;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class SearchRestaurantController implements SearchRestaurantControllerDocs {

    private final SearchRestaurantUseCase searchRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public SearchRestaurantController(SearchRestaurantUseCase searchRestaurantUseCase, RestaurantMapper restaurantMapper) {
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable Long id) {
        var restaurant = searchRestaurantUseCase.findById(id);
        return ResponseEntity.ok(restaurantMapper.toResponseDTO(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Restaurant> restaurants = searchRestaurantUseCase.findAll(page, size);

        List<RestaurantResponseDTO> responseList = restaurants.stream()
                .map(restaurantMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}
