package pos.java.bora_comer.infra.delivery.pedidoItem;

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

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.core.usercase.pedidoItem.SearchPedidoItemUseCase;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createResponseDTOWithId;

@WebMvcTest(SearchPedidoItemController.class)
public class SearchPedidoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchPedidoItemUseCase searchPedidoItemUseCase;

    @MockBean
    private PedidoItemMapper pedidoItemMapper;

    @Test
    void shouldFindPedidoItemByIdSuccessfully() throws Exception {
        Long id = 10L;

        PedidoItem mockPedidoItem = createDefaultWithId();

        PedidoItemResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchPedidoItemUseCase.findById(id)).thenReturn(mockPedidoItem);
        Mockito.when(pedidoItemMapper.toResponseDTO(mockPedidoItem)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedidoitems/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.menu_item_id").value(1))
                .andExpect(jsonPath("$.pedido_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));
        Mockito.verify(searchPedidoItemUseCase, Mockito.times(1)).findById(id);
        Mockito.verify(pedidoItemMapper, Mockito.times(1)).toResponseDTO(mockPedidoItem);
    }

    @Test
    void shouldFindAllPedidoItemSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        PedidoItem pedidoItem1 = createDefaultWithId();

        PedidoItem pedidoItem2 = createDefaultWithId();

        Page<PedidoItem> pageResult = new PageImpl<>(List.of(pedidoItem1, pedidoItem2), PageRequest.of(page, size), 2);

        PedidoItemResponseDTO dto1 = createResponseDTOWithId();

        PedidoItemResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchPedidoItemUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(pedidoItemMapper.toResponseDTO(pedidoItem1)).thenReturn(dto1);
        Mockito.when(pedidoItemMapper.toResponseDTO(pedidoItem2)).thenReturn(dto2);

        mockMvc.perform(get("/pedidoitems")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].pedido_id").value(1L))
                .andExpect(jsonPath("$[0].menu_item_id").value(1L))
                .andExpect(jsonPath("$[0].quantidade").value(2))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].pedido_id").value(1L))
                .andExpect(jsonPath("$[1].menu_item_id").value(1L))
                .andExpect(jsonPath("$[1].quantidade").value(2))
                ;
                
    }
}
