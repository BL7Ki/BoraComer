package pos.java.bora_comer.infra.graphql.input.order;

import java.time.LocalDateTime;

public record CreateOrderInput(
        LocalDateTime dateTimeOrder,
        Boolean delivery,
        Long restaurantId,
        Long userId
) {}