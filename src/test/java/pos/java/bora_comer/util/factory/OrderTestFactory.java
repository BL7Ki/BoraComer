package pos.java.bora_comer.util.factory;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderTestFactory {

    private static final Long DEFAULT_ID = 999L;
    private static final String DEFAULT_CLIENTE_ID = "client-xyz-123";
    private static final OrderStatusEnum DEFAULT_STATUS = OrderStatusEnum.CREATED;
    private static final LocalDateTime DEFAULT_DATA_CRIACAO = LocalDateTime.of(2025, 9, 25, 10, 0);
    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal("150.75");


    public static Order createDefaultOrder() {
        return new Order(
                DEFAULT_ID,
                DEFAULT_CLIENTE_ID,
                DEFAULT_STATUS,
                DEFAULT_DATA_CRIACAO,
                DEFAULT_VALOR_TOTAL
        );
    }

    public static Order createOrderWithStatus(OrderStatusEnum status) {
        return new Order(
                DEFAULT_ID,
                DEFAULT_CLIENTE_ID,
                status,
                DEFAULT_DATA_CRIACAO,
                DEFAULT_VALOR_TOTAL
        );
    }
}