package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.pedido.PedidoSearchGateway;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;


@Component
public class PedidoSearchGatewayImpl implements PedidoSearchGateway {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoSearchGatewayImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Pedido com ID " + id + " não encontrado."));
    }

    @Override
    public Page<Pedido> findAll(int page, int size) {
        return pedidoRepository.findAll(PageRequest.of(page, size))
                .map(pedidoMapper::toDomain);
    }
}
