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
import java.time.LocalDateTime;

@Controller
public class MutationResolver {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderService orderService;

    public MutationResolver(UserService userService,
                            RestaurantService restaurantService,
                            CreateOrderUseCase createOrderUseCase,
                            OrderService orderService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.createOrderUseCase = createOrderUseCase;
        this.orderService = orderService;
    }

    // --- User Mutations ---
    @MutationMapping
    public User createUser(
            @Argument String name,
            @Argument String email,
            @Argument String username,
            @Argument String password) {
        return userService.create(name, email, username, password);
    }

    // --- Restaurant Mutations ---
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
        return orderService.finalize(id);
    }
}