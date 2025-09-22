package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.pedido.PedidoDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;

@Component
public class PedidoDeleteGatewayImpl implements PedidoDeleteGateway {

    private final PedidoRepository pedidoRepository;

    public PedidoDeleteGatewayImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var pedidoEntity = pedidoRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Pedido com ID " + id + " não encontrado."));

        pedidoRepository.delete(pedidoEntity);
    }
}
