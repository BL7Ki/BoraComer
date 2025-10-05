package pos.java.bora_comer.infra.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.infra.service.OrderService;
import pos.java.bora_comer.infra.service.RestaurantService;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;
import pos.java.bora_comer.core.errors.UserDomainException;

import java.time.LocalDateTime;

@Controller
public class MutationResolver {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase; // validar essa parte
    private final RestaurantService restaurantService;
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderService orderService;

    public MutationResolver(
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            RestaurantService restaurantService,
            CreateOrderUseCase createOrderUseCase,
            OrderService orderService) {

        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.restaurantService = restaurantService;
        this.createOrderUseCase = createOrderUseCase;
        this.orderService = orderService;
    }

    @MutationMapping
    public User createUser(
            @Argument String name,
            @Argument String email,
            @Argument String username,
            @Argument String password) throws UserDomainException {

        User userToCreate = User.create(name, email, username, password, null, null);

        return createUserUseCase.execute(userToCreate);
    }

    @MutationMapping
    public Restaurant createRestaurant(
            @Argument String name,
            @Argument String address,
            @Argument String cuisineType,
            @Argument String openingHours) {
        return restaurantService.create(name, address, cuisineType, openingHours);
    }

    @MutationMapping
    public Restaurant updateRestaurant(@Argument Long id, @Argument String name, @Argument String address, @Argument String cuisineType, @Argument String openingHours) {
        return restaurantService.update(id, name, address, cuisineType, openingHours);
    }

    @MutationMapping
    public Boolean deleteRestaurant(@Argument Long id) {
        restaurantService.delete(id);
        return true;
    }

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
        return orderService.finalize(id);
    }
}