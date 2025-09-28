package pos.java.bora_comer.core.usercase.pedidoItem;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchPedidoItemUseCase {

    PedidoItem findById(Long id) throws SummerNotFoundException;

    Page<PedidoItem> findAll(int page, int size) throws PedidoItemDomainException;
}
