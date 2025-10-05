package pos.java.bora_comer.infra.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.infra.service.OrderService;
import pos.java.bora_comer.infra.service.RestaurantService;
import pos.java.bora_comer.core.errors.UserDomainException;

import java.util.List;

@Controller
public class QueryResolver {

    private final SearchUserUseCase searchUserUseCase;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    public QueryResolver(SearchUserUseCase searchUserUseCase, RestaurantService restaurantService, OrderService orderService) {
        this.searchUserUseCase = searchUserUseCase;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    // --- User Queries ---
    @QueryMapping
    public User userByUsername(@Argument String username) throws UserDomainException {
        return searchUserUseCase.findByUsername(username);
    }

    @QueryMapping
    public List<User> allUsers() {
        return searchUserUseCase.findAll(0, Integer.MAX_VALUE).getContent();
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