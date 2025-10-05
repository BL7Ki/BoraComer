package pos.java.bora_comer.infra.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.core.usercase.order.FinalizeOrderUseCase;
import pos.java.bora_comer.core.usercase.restaurant.CreateRestaurantUseCase;
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;
import pos.java.bora_comer.core.usercase.restaurant.DeleteRestaurantUseCase;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;
import pos.java.bora_comer.core.errors.UserDomainException;

import java.time.LocalDateTime;

@Controller
public class MutationResolver {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final FinalizeOrderUseCase finalizeOrderUseCase;


    public MutationResolver(
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            CreateRestaurantUseCase createRestaurantUseCase,
            UpdateRestaurantUseCase updateRestaurantUseCase,
            DeleteRestaurantUseCase deleteRestaurantUseCase,
            CreateOrderUseCase createOrderUseCase,
            FinalizeOrderUseCase finalizeOrderUseCase) {

        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.createOrderUseCase = createOrderUseCase;
        this.finalizeOrderUseCase = finalizeOrderUseCase;
    }

    // --- User Mutations ---
    @MutationMapping
    public User createUser(
            @Argument String name,
            @Argument String email,
            @Argument String username,
            @Argument String password,
            @Argument UserRoleEnum userRoleEnum,
            @Argument UserTypeNameEnum userTypeNameEnum) throws UserDomainException {

        User userToCreate = User.create(name, email, username, password, userRoleEnum, userTypeNameEnum);

        return createUserUseCase.execute(userToCreate);
    }

    // --- Restaurant Mutations ---
    @MutationMapping
    public Restaurant createRestaurant(
            @Argument String name,
            @Argument String address,
            @Argument String cuisineType,
            @Argument String openingHours,
            @Argument Long ownerId) {

        Restaurant restaurantToCreate = Restaurant.create(name, address, cuisineType, openingHours, ownerId);

        return createRestaurantUseCase.execute(restaurantToCreate);
    }

    @MutationMapping
    public Restaurant updateRestaurant(
            @Argument Long id,
            @Argument String name,
            @Argument String address,
            @Argument String cuisineType,
            @Argument String openingHours,
            @Argument Long ownerId) {

        Restaurant restaurantToUpdate = Restaurant.create(id, name, address, cuisineType, openingHours, ownerId);

        return updateRestaurantUseCase.execute(restaurantToUpdate);
    }

    @MutationMapping
    public Boolean deleteRestaurant(@Argument Long id) {
        deleteRestaurantUseCase.execute(id);
        return true;
    }

    // --- Order Mutations ---
    @MutationMapping
    public Order createOrder(
            @Argument Long userId,
            @Argument Long restaurantId,
            @Argument boolean delivery) {

        LocalDateTime now = LocalDateTime.now();

        Order orderToCreate = Order.create(
                now,
                delivery,
                restaurantId,
                userId
        );

        return createOrderUseCase.execute(orderToCreate);
    }

    @MutationMapping
    public Order finalizeOrder(@Argument Long id) {
        return finalizeOrderUseCase.execute(id);
    }
}