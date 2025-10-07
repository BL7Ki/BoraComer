package pos.java.bora_comer.infra.delivery.orderItem;

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

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.SearchOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createResponseDTOWithId;

@WebMvcTest(SearchOrderItemController.class)
public class SearchOrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchOrderItemUseCase searchOrderItemUseCase;

    @MockBean
    private OrderItemMapper orderItemMapper;

    @Test
    void shouldFindOrderItemByIdSuccessfully() throws Exception {
        Long id = 10L;

        OrderItem mockOrderItem = createDefaultWithId();

        OrderItemResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchOrderItemUseCase.findById(id)).thenReturn(mockOrderItem);
        Mockito.when(orderItemMapper.toResponseDTO(mockOrderItem)).thenReturn(responseDTO);

        mockMvc.perform(get("/orderitems/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.menu_item_id").value(1))
                .andExpect(jsonPath("$.pedido_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));
        Mockito.verify(searchOrderItemUseCase, Mockito.times(1)).findById(id);
        Mockito.verify(orderItemMapper, Mockito.times(1)).toResponseDTO(mockOrderItem);
    }

    @Test
    void shouldFindAllOrderItemSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        OrderItem orderItem1 = createDefaultWithId();

        OrderItem orderItem2 = createDefaultWithId();

        Page<OrderItem> pageResult = new PageImpl<>(List.of(orderItem1, orderItem2), PageRequest.of(page, size), 2);

        OrderItemResponseDTO dto1 = createResponseDTOWithId();

        OrderItemResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchOrderItemUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(orderItemMapper.toResponseDTO(orderItem1)).thenReturn(dto1);
        Mockito.when(orderItemMapper.toResponseDTO(orderItem2)).thenReturn(dto2);

        mockMvc.perform(get("/orderitems")
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
