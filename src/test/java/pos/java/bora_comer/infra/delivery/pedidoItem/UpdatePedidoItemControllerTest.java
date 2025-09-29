package pos.java.bora_comer.infra.delivery.pedidoItem;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.UpdatePedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createUpdateRequestDTOWithId;

import java.time.LocalDateTime;

@WebMvcTest(UpdatePedidoItemController.class)
public class UpdatePedidoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoItemMapper pedidoItemMapper;

    @MockBean
    private UpdatePedidoItemUseCase updatePedidoItemUseCase;

    @Test
    void shouldUpdatePedidoItemSuccessfully() throws Exception {
        Long id = 10L;
        Long pedidoId = 1L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoItemUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        PedidoItem existingPedidoItem = PedidoItem.create(  
                id,
                pedidoId,
                menuItemId,
                2,
                dateTime
        );

        Mockito.when(updatePedidoItemUseCase.findById(id)).thenReturn(existingPedidoItem);

        PedidoItem domainPedidoItem = PedidoItem.create(    
                id,
                pedidoId,
                menuItemId,
                updateRequestDTO.quantity(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(pedidoItemMapper.toDomain(updateRequestDTO, id, pedidoId, menuItemId)).thenReturn(domainPedidoItem);
        Mockito.when(updatePedidoItemUseCase.execute(domainPedidoItem)).thenReturn(domainPedidoItem);

        PedidoItemResponseDTO responseDTO = new PedidoItemResponseDTO(
                id,
                pedidoId,
                menuItemId,
                updateRequestDTO.quantity(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(pedidoItemMapper.toResponseDTO(domainPedidoItem)).thenReturn(responseDTO);

        mockMvc.perform(put("/pedidoitems/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.pedido_id").value(pedidoId))
                .andExpect(jsonPath("$.menu_item_id").value(menuItemId))
                .andExpect(jsonPath("$.quantidade").value(updateRequestDTO.quantity()));
    }
}
