package pos.java.bora_comer.infra.delivery.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.CreatePedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.*;

@WebMvcTest(CreatePedidoController.class)
class CreatePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoMapper pedidoMapper;

    @MockBean
    private CreatePedidoUseCase createPedidoUseCase;

    @Test
    void shouldCreatePedidoSuccessfully() throws Exception {
        // given (entrada do cliente)
        PedidoRequestDTO requestDTO = createRequestDTOWithId();

        Pedido domain = createDefaultWithId();

        PedidoResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(pedidoMapper.toDomain(any(PedidoRequestDTO.class))).thenReturn(domain);
        Mockito.when(createPedidoUseCase.execute(any(Pedido.class))).thenReturn(domain);
        Mockito.when(pedidoMapper.toResponseDTO(any(Pedido.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/pedidos/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.userId").value(1))       
                .andExpect(jsonPath("$.restaurantId").value(2))
                .andExpect(jsonPath("$.delivery").value(true))
                .andExpect(jsonPath("$.dateTimeOrder").value("2023-10-10T12:00:00"))
                ;

                
        // verificação de chamadas
        Mockito.verify(pedidoMapper).toDomain(requestDTO);
        Mockito.verify(createPedidoUseCase).execute(domain);
        Mockito.verify(pedidoMapper).toResponseDTO(domain);
    }
}
