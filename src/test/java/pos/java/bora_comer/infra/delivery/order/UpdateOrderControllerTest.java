//package pos.java.bora_comer.infra.delivery.order;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import pos.java.bora_comer.core.domain.order.Order;
//import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
//import pos.java.bora_comer.core.mapper.order.OrderMapper;
//import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
//import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
//import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
//// Importe as classes de segurança que estão causando a falha
//import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;
//import pos.java.bora_comer.infra.service.JwtService;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static pos.java.bora_comer.util.factory.OrderTestFactory.createUpdateRequestDTO;
//import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;
//
//@WebMvcTest(
//        controllers = UpdateOrderController.class,
//        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
//)
//public class UpdateOrderControllerTest {
//
//    private static final Long TEST_ID = 10L;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    // Dependências do Controller de Pedidos (foco do teste)
//    @MockBean
//    private OrderMapper orderMapper;
//
//    @MockBean
//    private UpdateOrderUseCase updateOrderUseCase;
//
//    // SOLUÇÃO: Mockar as dependências de segurança que causam o erro de contexto
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//
//    @Test
//    void shouldUpdateOrderSuccessfully() throws Exception {
//        OrderUpdateRequestDTO requestDTO = createUpdateRequestDTO();
//
//        Order updatedDomain = createDefaultWithId(TEST_ID, OrderStatusEnum.IN_PROGRESS);
//
//        OrderResponseDTO responseDTO = new OrderResponseDTO(
//                TEST_ID,
//                requestDTO.dateTimeOrder(),
//                requestDTO.delivery(),
//                requestDTO.restaurantId(),
//                requestDTO.userId(),
//                requestDTO.status(),
//                requestDTO.dateTimeOrder()
//        );
//
//        when(orderMapper.toDomain(eq(requestDTO), eq(TEST_ID), any(Long.class), any(Long.class)))
//                .thenReturn(updatedDomain);
//
//        when(updateOrderUseCase.execute(eq(updatedDomain)))
//                .thenReturn(updatedDomain);
//
//        when(orderMapper.toResponseDTO(eq(updatedDomain)))
//                .thenReturn(responseDTO);
//
//        mockMvc.perform(put("/orders/{id}", TEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(responseDTO.id()))
//                .andExpect(jsonPath("$.delivery").value(responseDTO.delivery()))
//                .andExpect(jsonPath("$.restaurantId").value(responseDTO.restaurantId()))
//                .andExpect(jsonPath("$.usuario_id").value(responseDTO.userId()));
//
//
//        verify(orderMapper, times(1)).toDomain(eq(requestDTO), eq(TEST_ID), any(Long.class), any(Long.class));
//        verify(updateOrderUseCase, times(1)).execute(updatedDomain);
//        verify(orderMapper, times(1)).toResponseDTO(updatedDomain);
//    }
//}