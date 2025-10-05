//package pos.java.bora_comer.infra.delivery.order;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import pos.java.bora_comer.core.domain.order.Order;
//import pos.java.bora_comer.core.mapper.order.OrderMapper;
//import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;
//import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
//import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;
//import pos.java.bora_comer.infra.service.JwtService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;
//import static pos.java.bora_comer.util.factory.OrderTestFactory.createResponseDTO;
//
//@WebMvcTest(
//        controllers = SearchOrderController.class,
//        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
//)
//public class SearchOrderControllerTest {
//
//    private static final Long TEST_ID = 10L;
//    private static final Long DEFAULT_RESTAURANT_ID = 1L;
//    private static final Long DEFAULT_USER_ID = 1L;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SearchOrderUseCase searchOrderUseCase;
//
//    @MockBean
//    private OrderMapper orderMapper;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Test
//    void shouldFindOrderByIdSuccessfully() throws Exception {
//        Long id = TEST_ID;
//
//        Order mockOrder = createDefaultWithId(id, null);
//
//        OrderResponseDTO responseDTO = createResponseDTO(
//                id, mockOrder.getDateTimeOrder(), mockOrder.isDelivery(),
//                DEFAULT_RESTAURANT_ID, DEFAULT_USER_ID, mockOrder.getStatus(),
//                mockOrder.getLastModifiedDate()
//        );
//
//        Mockito.when(searchOrderUseCase.findById(id)).thenReturn(mockOrder);
//        Mockito.when(orderMapper.toResponseDTO(mockOrder)).thenReturn(responseDTO);
//
//        mockMvc.perform(get("/orders/{id}", id)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.usuario_id").value(DEFAULT_USER_ID))
//                .andExpect(jsonPath("$.restaurant_id").value(DEFAULT_RESTAURANT_ID))
//                .andExpect(jsonPath("$.delivery").value(true));
//
//        Mockito.verify(searchOrderUseCase, Mockito.times(1)).findById(id);
//        Mockito.verify(orderMapper, Mockito.times(1)).toResponseDTO(mockOrder);
//    }
//
//    @Test
//    void shouldFindAllOrderSuccessfully() throws Exception {
//        int page = 0;
//        int size = 2;
//        Long id1 = 1L;
//        Long id2 = 2L;
//
//        Order order1 = createDefaultWithId(id1, null);
//        Order order2 = createDefaultWithId(id2, null);
//
//        OrderResponseDTO dto1 = createResponseDTO(id1, order1.getDateTimeOrder(), order1.isDelivery(), DEFAULT_RESTAURANT_ID, DEFAULT_USER_ID, order1.getStatus(), order1.getLastModifiedDate());
//        OrderResponseDTO dto2 = createResponseDTO(id2, order2.getDateTimeOrder(), order2.isDelivery(), DEFAULT_RESTAURANT_ID, DEFAULT_USER_ID, order2.getStatus(), order2.getLastModifiedDate());
//
//        Page<Order> pageResultDomain = new PageImpl<>(List.of(order1, order2), PageRequest.of(page, size), 2);
//
//        List<OrderResponseDTO> dtoList = List.of(dto1, dto2);
//
//        Mockito.when(searchOrderUseCase.findAll(page, size)).thenReturn(pageResultDomain);
//
//        Mockito.when(orderMapper.toResponseDTO(order1)).thenReturn(dto1);
//        Mockito.when(orderMapper.toResponseDTO(order2)).thenReturn(dto2);
//
//        mockMvc.perform(get("/orders")
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content[0].id").value(id1))
//                .andExpect(jsonPath("$.content[0].restaurant_id").value(DEFAULT_RESTAURANT_ID))
//                .andExpect(jsonPath("$.content[0].usuario_id").value(DEFAULT_USER_ID))
//                .andExpect(jsonPath("$.content[0].delivery").value(true))
//                .andExpect(jsonPath("$.content[1].id").value(id2))
//                .andExpect(jsonPath("$.content[1].restaurant_id").value(DEFAULT_RESTAURANT_ID))
//                .andExpect(jsonPath("$.content[1].usuario_id").value(DEFAULT_USER_ID))
//                .andExpect(jsonPath("$.content[1].delivery").value(true))
//                .andExpect(jsonPath("$.totalPages").value(1));
//
//        Mockito.verify(searchOrderUseCase, Mockito.times(1)).findAll(page, size);
//        Mockito.verify(orderMapper, Mockito.times(1)).toResponseDTO(order1);
//        Mockito.verify(orderMapper, Mockito.times(1)).toResponseDTO(order2);
//    }
//}