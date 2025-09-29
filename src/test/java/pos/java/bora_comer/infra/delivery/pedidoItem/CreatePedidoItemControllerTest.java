package pos.java.bora_comer.infra.delivery.pedidoItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.CreatePedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createRequestDTOWithId;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefaultWithId;

@WebMvcTest(CreatePedidoItemController.class)
class CreatePedidoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoItemMapper pedidoItemMapper;

    @MockBean
    private CreatePedidoItemUseCase createPedidoItemUseCase;

    @Test
    void shouldCreatePedidoItemSuccessfully() throws Exception {
        // given (entrada do cliente)
        PedidoItemRequestDTO requestDTO = createRequestDTOWithId();

        PedidoItem domain = createDefaultWithId();

        PedidoItemResponseDTO responseDTO = createResponseDTOWithId();
        
        // mocks
        Mockito.when(pedidoItemMapper.toDomain(any(PedidoItemRequestDTO.class))).thenReturn(domain);
        Mockito.when(createPedidoItemUseCase.execute(any(PedidoItem.class))).thenReturn(domain);
        Mockito.when(pedidoItemMapper.toResponseDTO(any(PedidoItem.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/pedidoitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/pedidoitems/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.pedido_id").value(1))       
                .andExpect(jsonPath("$.menu_item_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));
                

                
        // verificação de chamadas
        Mockito.verify(pedidoItemMapper).toDomain(requestDTO);
        Mockito.verify(createPedidoItemUseCase).execute(domain);
        Mockito.verify(pedidoItemMapper).toResponseDTO(domain);
    }
}
