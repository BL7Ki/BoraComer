package pos.java.bora_comer.infra.delivery.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.UpdatePedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createUpdateRequestDTOWithId;

import java.time.LocalDateTime;

@WebMvcTest(UpdatePedidoController.class)
public class UpdatePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoMapper pedidoMapper;

    @MockBean
    private UpdatePedidoUseCase updatePedidoUseCase;

    @Test
    void shouldUpdatePedidoSuccessfully() throws Exception {
        Long id = 10L;
        Long restauranteId = 1L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        Pedido existingPedido = Pedido.create(  
                id,
                dateTime,
                true,
                restauranteId,
                userId,
                dateTime
        );

        Mockito.when(updatePedidoUseCase.findById(id)).thenReturn(existingPedido);

        Pedido domainPedido = Pedido.create(    
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                updateRequestDTO.userId(),
                updateRequestDTO.restaurantId(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(pedidoMapper.toDomain(updateRequestDTO, id, restauranteId, userId)).thenReturn(domainPedido);
        Mockito.when(updatePedidoUseCase.execute(domainPedido)).thenReturn(domainPedido);

        PedidoResponseDTO responseDTO = new PedidoResponseDTO(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                userId,
                restauranteId,
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(pedidoMapper.toResponseDTO(domainPedido)).thenReturn(responseDTO);

        mockMvc.perform(put("/pedidos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.delivery").value(updateRequestDTO.delivery()))
                .andExpect(jsonPath("$.restaurante_id").value(restauranteId))
                .andExpect(jsonPath("$.usuario_id").value(userId));
    }
}
