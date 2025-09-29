package pos.java.bora_comer.util.factory;

import java.time.LocalDateTime;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;

public class PedidoItemTestFactory {
    
    // Construtor privado para impedir instanciação
    private PedidoItemTestFactory() {
        // impede instanciação
    }
    
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    
    public static PedidoItem createDefault() {
        return PedidoItem.create(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static PedidoItem createDefaultWithId() {
        return PedidoItem.create(
                10L,
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static PedidoItemResponseDTO createResponseDTOWithId() {
        return new PedidoItemResponseDTO(
                10L,
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static PedidoItemRequestDTO createRequestDTOWithId() {
        return new PedidoItemRequestDTO(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static PedidoItemUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new PedidoItemUpdateRequestDTO(
                1L,
                1L,
                2,
                dateTime
        );
    }

    public static PedidoItem createCustom(Long id, Long pedidoId, Long menuItemId, int quantity, LocalDateTime lastModifiedDate) {
        return PedidoItem.create(
                id,
                pedidoId,
                menuItemId,
                quantity,
                lastModifiedDate
        );
    }
}

