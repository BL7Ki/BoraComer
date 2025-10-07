package pos.java.bora_comer.infra.delivery.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createResponseDTOWithId;

@WebMvcTest(
        controllers = SearchOrderController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class SearchOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchOrderUseCase searchOrderUseCase;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldFindOrderByIdSuccessfully() throws Exception {
        Long id = 10L;

        Order mockOrder = createDefaultWithId();

        OrderResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchOrderUseCase.findById(id)).thenReturn(mockOrder);
        Mockito.when(orderMapper.toResponseDTO(mockOrder)).thenReturn(responseDTO);

        mockMvc.perform(get("/orders/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.usuario_id").value(1))
                .andExpect(jsonPath("$.restaurante_id").value(1))
                .andExpect(jsonPath("$.delivery").value(true));
        Mockito.verify(searchOrderUseCase, Mockito.times(1)).findById(id);
        Mockito.verify(orderMapper, Mockito.times(1)).toResponseDTO(mockOrder);
    }

    @Test
    void shouldFindAllOrderSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        Order order1 = createDefaultWithId();

        Order order2 = createDefaultWithId();

        Page<Order> pageResult = new PageImpl<>(List.of(order1, order2), PageRequest.of(page, size), 2);

        OrderResponseDTO dto1 = createResponseDTOWithId();

        OrderResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchOrderUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(orderMapper.toResponseDTO(order1)).thenReturn(dto1);
        Mockito.when(orderMapper.toResponseDTO(order2)).thenReturn(dto2);

        mockMvc.perform(get("/orders")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].restaurante_id").value(1L))
                .andExpect(jsonPath("$[0].usuario_id").value(1L))
                .andExpect(jsonPath("$[0].delivery").value(true))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].restaurante_id").value(1L))
                .andExpect(jsonPath("$[1].usuario_id").value(1L))
                .andExpect(jsonPath("$[1].delivery").value(true));
                ;
                
    }
}
