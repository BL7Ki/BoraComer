package pos.java.bora_comer.util.factory;

import java.time.LocalDateTime;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;

public class PedidoTestFactory {
    
    // Construtor privado para impedir instanciação
    private PedidoTestFactory() {
        // impede instanciação
    }
     private static final String dateStr = "2024-10-10T12:00:00";
     private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);
    
    public static Pedido createDefault() {
        return Pedido.create(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static Pedido createDefaultWithId() {
        return Pedido.create(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static PedidoResponseDTO createResponseDTOWithId() {
        return new PedidoResponseDTO(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static PedidoRequestDTO createRequestDTOWithId() {
        return new PedidoRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
    }

    public static PedidoUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new PedidoUpdateRequestDTO(
                dateTime,
                false,
                1L,
                1L,
                dateTime
        );
    }

    public static Pedido createCustom(Long id, LocalDateTime dateTime, boolean delivery, Long restaurantId, Long userId, LocalDateTime lastModifiedDate) {
        return Pedido.create(
                id,
                dateTime,
                delivery,
                restaurantId,
                userId,
                lastModifiedDate
        );
    }
}

