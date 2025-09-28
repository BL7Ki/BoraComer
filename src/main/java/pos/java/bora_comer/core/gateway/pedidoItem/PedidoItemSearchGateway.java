package pos.java.bora_comer.core.gateway.pedidoItem;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;


public interface PedidoItemSearchGateway {

    PedidoItem findById(Long id);
    
    Page<PedidoItem> findAll(int page, int size);
}
