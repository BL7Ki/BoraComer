package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.graphql.dto.PageResponse;
import pos.java.bora_comer.infra.graphql.input.order.CreateOrderInput;
import pos.java.bora_comer.infra.graphql.input.order.UpdateOrderInput;

import java.time.LocalDateTime;

@Controller
public class OrderResolver {

    private final CreateOrderUseCase createUseCase;
    private final SearchOrderUseCase searchUseCase;
    private final UpdateOrderUseCase updateUseCase;

    public OrderResolver(CreateOrderUseCase createUseCase,
                         SearchOrderUseCase searchUseCase,
                         UpdateOrderUseCase updateUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
        this.updateUseCase = updateUseCase;
    }

    // --- QUERIES ---

    @QueryMapping
    public Order order(@Argument Long id) {
        return searchUseCase.findById(id);
    }

    @QueryMapping
    public PageResponse<Order> orders(@Argument int page, @Argument int size) {
        return PageResponse.fromPage(searchUseCase.findAll(page, size));
    }

    // --- MUTATIONS ---

    @MutationMapping
    public Order createOrder(@Argument CreateOrderInput input) {
        Order newOrder = mapInputToOrder(input);
        return createUseCase.execute(newOrder);
    }

    @MutationMapping
    public Order updateOrder(@Argument Long id, @Argument UpdateOrderInput input) {
        Order existingOrder = updateUseCase.findById(id);
        Order updatedOrder = applyUpdateInput(existingOrder, input);
        return updateUseCase.execute(updatedOrder);
    }

    @MutationMapping
    public Order finalizeOrder(@Argument Long id) {
        Order existingOrder = updateUseCase.findById(id);

        // APLICA A REGRA DE NEGÓCIO: Isso retorna uma NOVA instância de Order com o status = FINALIZED
        Order finalizedOrder = existingOrder.finalizer();

        return updateUseCase.execute(finalizedOrder);
    }

    // --- LÓGICA DE MAPEAMENTO INTERNA ---

    private Order mapInputToOrder(CreateOrderInput input) {
        return Order.create(
                input.dateTimeOrder(),
                input.delivery(),
                input.restaurantId(),
                input.userId(),
                LocalDateTime.now()
        );
    }

    private Order applyUpdateInput(Order existingOrder, UpdateOrderInput input) {

        return existingOrder.toBuilder()
                .dateTimeOrder(input.dateTimeOrder() != null ? input.dateTimeOrder() : existingOrder.getDateTimeOrder())
                .delivery(input.delivery() != null ? input.delivery() : existingOrder.isDelivery())
                .restaurantId(input.restaurantId() != null ? input.restaurantId() : existingOrder.getRestaurantId())
                .userId(input.userId() != null ? input.userId() : existingOrder.getUserId())
                .build();
    }
}