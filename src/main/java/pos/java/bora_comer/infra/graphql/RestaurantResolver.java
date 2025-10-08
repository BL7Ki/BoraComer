package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.usercase.restaurant.CreateRestaurantUseCase;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;
import pos.java.bora_comer.core.usercase.restaurant.DeleteRestaurantUseCase;
import pos.java.bora_comer.infra.graphql.dto.PageResponse;
import pos.java.bora_comer.infra.graphql.input.restaurant.CreateRestaurantInput;
import pos.java.bora_comer.infra.graphql.input.restaurant.UpdateRestaurantInput;

@Controller
public class RestaurantResolver {

    private final CreateRestaurantUseCase createUseCase;
    private final SearchRestaurantUseCase searchUseCase;
    private final UpdateRestaurantUseCase updateUseCase;
    private final DeleteRestaurantUseCase deleteUseCase;

    public RestaurantResolver(CreateRestaurantUseCase createUseCase,
                              SearchRestaurantUseCase searchUseCase,
                              UpdateRestaurantUseCase updateUseCase,
                              DeleteRestaurantUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    // --- QUERIES ---

    @QueryMapping
    public Restaurant restaurant(@Argument Long id) {
        return searchUseCase.findById(id);
    }

    @QueryMapping
    public PageResponse<Restaurant> restaurants(
            @Argument int page,
            @Argument int size,
            @Argument String cuisineType
    ) {
        return PageResponse.fromPage(searchUseCase.findAll(page, size, cuisineType));
    }

    // --- MUTATIONS ---

    @MutationMapping
    public Restaurant createRestaurant(@Argument CreateRestaurantInput input) {
        Restaurant newRestaurant = mapInputToRestaurant(input);
        return createUseCase.execute(newRestaurant);
    }

    @MutationMapping
    public Restaurant updateRestaurant(@Argument Long id, @Argument UpdateRestaurantInput input) {
        Restaurant existingRestaurant = updateUseCase.findById(id);
        Restaurant updatedRestaurant = applyUpdateInput(existingRestaurant, input);
        return updateUseCase.execute(updatedRestaurant);
    }

    @MutationMapping
    public Boolean deleteRestaurant(@Argument Long id) {
        deleteUseCase.execute(id);
        return true;
    }

    // --- LÓGICA DE MAPEAMENTO INTERNA ---

    private Restaurant mapInputToRestaurant(CreateRestaurantInput input) {
        return Restaurant.create(
                input.name(),
                input.address(),
                input.cuisineType(),
                input.openingHours(),
                input.ownerId()
        );
    }

    private Restaurant applyUpdateInput(Restaurant existingRestaurant, UpdateRestaurantInput input) {

        return existingRestaurant.toBuilder()
                .name(input.name() != null ? input.name() : existingRestaurant.getName())
                .address(input.address() != null ? input.address() : existingRestaurant.getAddress())
                .cuisineType(input.cuisineType() != null ? input.cuisineType() : existingRestaurant.getCuisineType())
                .openingHours(input.openingHours() != null ? input.openingHours() : existingRestaurant.getOpeningHours())
                .ownerId(input.ownerId() != null ? input.ownerId() : existingRestaurant.getOwnerId())
                .build();
    }
}
