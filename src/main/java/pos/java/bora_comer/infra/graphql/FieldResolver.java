package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;
// IMPORTANTE: Adicione o UseCase de busca de pedidos por usuário, se existir:
// import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;


@Controller
public class FieldResolver {

    private final SearchUserUseCase searchUserUseCase;
    private final SearchRestaurantUseCase searchRestaurantUseCase;
    // private final SearchOrderUseCase searchOrderUseCase; // Adicione este se necessário

    public FieldResolver(SearchUserUseCase searchUserUseCase, SearchRestaurantUseCase searchRestaurantUseCase) {
        this.searchUserUseCase = searchUserUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
    }

    // --- RESOLVERS PARA O TIPO 'Order' ---

    @SchemaMapping(typeName = "Order", field = "user")
    public User getUser(Order order) {
        // Assume que Order tem um método getUserId()
        return searchUserUseCase.findById(order.getUserId());
    }

    @SchemaMapping(typeName = "Order", field = "restaurant")
    public Restaurant getRestaurant(Order order) {
        // Assume que Order tem um método getRestaurantId()
        return searchRestaurantUseCase.findById(order.getRestaurantId());
    }

    // --- RESOLVERS PARA O TIPO 'Restaurant' ---

    @SchemaMapping(typeName = "Restaurant", field = "owner")
    public User getOwner(Restaurant restaurant) {
        // Assume que Restaurant tem um método getOwnerId()
        return searchUserUseCase.findById(restaurant.getOwnerId());
    }

    // ⚠️ Idealmente, implementaria Restaurant.orders aqui, buscando todos os pedidos para o ID do restaurante.

    // --- RESOLVERS PARA O TIPO 'User' ---

    @SchemaMapping(typeName = "User", field = "orders")
    public List<Order> getOrders(User user) {
        // ⚠️ Esta é a principal fonte do problema N+1.
        // Se você usar DataLoader, o retorno deve ser um CompletableFuture<List<Order>>.
        // Aqui, chamamos um método que buscaria todos os pedidos feitos por este usuário.
        // return searchOrderUseCase.findAllByUserId(user.getId());
        return List.of(); // Substitua pela lógica real
    }

    @SchemaMapping(typeName = "User", field = "address")
    public Address getAddress(User user) {
        // Assumindo que o objeto User de domínio já contém o objeto Address
        return user.getAddress();
    }

    // ⚠️ Se 'UserType' no domínio for apenas um ID/Enum, você precisará buscá-lo aqui também.
}
