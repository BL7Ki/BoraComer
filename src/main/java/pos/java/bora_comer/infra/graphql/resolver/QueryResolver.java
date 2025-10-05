package pos.java.bora_comer.infra.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.service.OrderService;
import pos.java.bora_comer.infra.service.RestaurantService;

import java.util.List;

@Controller
public class QueryResolver {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    public QueryResolver(UserService userService, RestaurantService restaurantService, OrderService orderService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    // --- User Queries ---
    @QueryMapping
    public UserEntity userById(@Argument Long id) {
        return userService.findById(id);
    }

    @QueryMapping
    public List<UserEntity> allUsers() {
        return userService.findAll();
    }

    // --- Restaurant Queries ---
    @QueryMapping
    public Restaurant restaurantById(@Argument Long id) {
        return restaurantService.findById(id);
    }

    @QueryMapping
    public List<Restaurant> allRestaurants() {
        return restaurantService.findAll();
    }

    // --- Order Queries ---
    @QueryMapping
    public Order orderById(@Argument Long id) {
        return orderService.findById(id);
    }

    @QueryMapping
    public List<Order> allOrders() {
        return orderService.findAll();
    }
}