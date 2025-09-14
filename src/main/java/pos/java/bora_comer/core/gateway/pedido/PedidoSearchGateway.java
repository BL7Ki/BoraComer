package pos.java.bora_comer.core.gateway.pedido;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.pedido.Pedido;


public interface PedidoSearchGateway {

    Pedido findById(Long id);

    Page<Pedido> findAll(int page, int size);
}
