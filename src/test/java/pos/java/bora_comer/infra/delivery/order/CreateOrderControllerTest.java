package pos.java.bora_comer.infra.delivery.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createRequestDTOWithId;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CreateOrderController.class)
@AutoConfigureMockMvc(addFilters = false)

class CreateOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        // given (entrada do cliente)
        OrderRequestDTO requestDTO = createRequestDTOWithId();

        Order domain = createDefaultWithId();

        OrderResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(orderMapper.toDomain(any(OrderRequestDTO.class))).thenReturn(domain);
        Mockito.when(createOrderUseCase.execute(any(Order.class))).thenReturn(domain);
        Mockito.when(orderMapper.toResponseDTO(any(Order.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/orders")
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/orders/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.usuario_id").value(1))
                .andExpect(jsonPath("$.restaurante_id").value(1))
                .andExpect(jsonPath("$.delivery").value(true))
                .andExpect(jsonPath("$.data_hora").value("2024-10-10T12:00:00"));                ;


        // verificação de chamadas
        Mockito.verify(orderMapper).toDomain(requestDTO);
        Mockito.verify(createOrderUseCase).execute(domain);
        Mockito.verify(orderMapper).toResponseDTO(domain);
    }
}
