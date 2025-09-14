package pos.java.bora_comer.core.gateway.pedido;

import pos.java.bora_comer.core.domain.pedido.Pedido;

public interface PedidoCreateGateway {

    boolean existsByNameAndRestaurantIdAndUserId(String name, Long restaurantId, Long userId);

    Pedido save(Pedido pedido);
}
