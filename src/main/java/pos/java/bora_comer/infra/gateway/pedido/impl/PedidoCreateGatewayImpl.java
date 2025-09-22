package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.gateway.pedido.PedidoCreateGateway;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;

@Component
public class PedidoCreateGatewayImpl implements PedidoCreateGateway {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoCreateGatewayImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    
    @Transactional
    @Override
    public Pedido save(Pedido pedido) {
        if (pedido == null) {
            throw new PedidoDomainException("Pedido não pode ser nulo");
        }
        var pedidoEntity = pedidoMapper.toEntity(pedido);
        var savedEntity = pedidoRepository.save(pedidoEntity);

        return pedidoMapper.toDomain(savedEntity);
    }
}
