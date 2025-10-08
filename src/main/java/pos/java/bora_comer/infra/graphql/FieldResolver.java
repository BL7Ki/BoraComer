package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;


@Controller
public class FieldResolver {

    private final SearchUserUseCase searchUserUseCase;
    private final SearchRestaurantUseCase searchRestaurantUseCase;
    private final SearchOrderUseCase searchOrderUseCase;

    public FieldResolver(
            SearchUserUseCase searchUserUseCase,
            SearchRestaurantUseCase searchRestaurantUseCase,
            SearchOrderUseCase searchOrderUseCase
    ) {
        this.searchUserUseCase = searchUserUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.searchOrderUseCase = searchOrderUseCase;
    }

    // --- RESOLVERS PARA O TIPO 'Order' ---

    @SchemaMapping(typeName = "Order", field = "user")
    public User getUser(Order order) {
        return searchUserUseCase.findById(order.getUserId());
    }

    @SchemaMapping(typeName = "Order", field = "restaurant")
    public Restaurant getRestaurant(Order order) {
        return searchRestaurantUseCase.findById(order.getRestaurantId());
    }

    // --- RESOLVERS PARA O TIPO 'Restaurant' ---

    @SchemaMapping(typeName = "Restaurant", field = "owner")
    public User getOwner(Restaurant restaurant) {
        return searchUserUseCase.findById(restaurant.getOwnerId());
    }

    // --- RESOLVERS PARA O TIPO 'User' ---

    @SchemaMapping(typeName = "User", field = "orders")
    public List<Order> getOrders(User user) {
        return searchOrderUseCase.findAllByUserId(user.getId());
    }

    @SchemaMapping(typeName = "User", field = "address")
    public Address getAddress(User user) {
        return user.getAddress();
    }
}