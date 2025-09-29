package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;

@Component
public class PedidoItemDeleteGatewayImpl implements PedidoItemDeleteGateway {

    private final PedidoItemRepository pedidoItemRepository;

    public PedidoItemDeleteGatewayImpl(PedidoItemRepository pedidoItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var pedidoItemEntity = pedidoItemRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Item de Pedido com ID " + id + " não encontrado."));

        pedidoItemRepository.delete(pedidoItemEntity);
    }
}
