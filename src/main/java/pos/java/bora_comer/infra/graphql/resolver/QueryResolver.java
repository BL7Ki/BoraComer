package pos.java.bora_comer.infra.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
import pos.java.bora_comer.core.errors.UserDomainException;

import java.util.List;

@Controller
public class QueryResolver {

    private final SearchUserUseCase searchUserUseCase;
    private final SearchRestaurantUseCase searchRestaurantUseCase;
    private final SearchOrderUseCase searchOrderUseCase;

    public QueryResolver(
            SearchUserUseCase searchUserUseCase,
            SearchRestaurantUseCase searchRestaurantUseCase,
            SearchOrderUseCase searchOrderUseCase) {

        this.searchUserUseCase = searchUserUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.searchOrderUseCase = searchOrderUseCase;
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
        return searchRestaurantUseCase.findById(id);
    }

    @QueryMapping
    public List<Restaurant> allRestaurants() {
        return searchRestaurantUseCase.findAll(0, Integer.MAX_VALUE).getContent();
    }

    // --- Order Queries ---
    @QueryMapping
    public Order orderById(@Argument Long id) {
        return searchOrderUseCase.findById(id);
    }

    @QueryMapping
    public List<Order> allOrders() {
        // Assumindo que 0, Integer.MAX_VALUE busca todos
        return searchOrderUseCase.findAll(0, Integer.MAX_VALUE).getContent();
    }
}