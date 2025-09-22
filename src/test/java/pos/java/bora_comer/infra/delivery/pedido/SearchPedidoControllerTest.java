package pos.java.bora_comer.infra.delivery.pedido;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.core.usercase.pedido.SearchPedidoUseCase;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createResponseDTOWithId;

@WebMvcTest(SearchPedidoController.class)
public class SearchPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchPedidoUseCase searchPedidoUseCase;

    @MockBean
    private PedidoMapper pedidoMapper;

    @Test
    void shouldFindPedidoByIdSuccessfully() throws Exception {
        Long id = 10L;

        Pedido mockPedido = createDefaultWithId();

        PedidoResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchPedidoUseCase.findById(id)).thenReturn(mockPedido);
        Mockito.when(pedidoMapper.toResponseDTO(mockPedido)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedidos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.restaurantId").value(2))
                .andExpect(jsonPath("$.delivery").value(true));
        Mockito.verify(searchPedidoUseCase, Mockito.times(1)).findById(id);
        Mockito.verify(pedidoMapper, Mockito.times(1)).toResponseDTO(mockPedido);
    }

    @Test
    void shouldFindAllPedidoSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        Pedido pedido1 = createDefaultWithId();

        Pedido pedido2 = createDefaultWithId();

        Page<Pedido> pageResult = new PageImpl<>(List.of(pedido1, pedido2), PageRequest.of(page, size), 2);

        PedidoResponseDTO dto1 = createResponseDTOWithId();

        PedidoResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchPedidoUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(pedidoMapper.toResponseDTO(pedido1)).thenReturn(dto1);
        Mockito.when(pedidoMapper.toResponseDTO(pedido2)).thenReturn(dto2);

        mockMvc.perform(get("/pedidos")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].restaurantId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].restaurantId").value(1L))
                .andExpect(jsonPath("$[1].userId").value(1L))
                ;
                
    }
}
