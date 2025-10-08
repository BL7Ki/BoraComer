package pos.java.bora_comer.infra.graphql.input.order;

import java.time.LocalDateTime;

public record UpdateOrderInput(
        LocalDateTime dateTimeOrder,
        Boolean delivery,
        LocalDateTime lastModifiedDate,
        Long restaurantId,
        Long userId
) {}