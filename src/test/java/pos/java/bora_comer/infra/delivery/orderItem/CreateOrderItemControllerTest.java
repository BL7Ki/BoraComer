package pos.java.bora_comer.infra.delivery.orderItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.CreateOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createRequestDTOWithId;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CreateOrderItemController.class)
@AutoConfigureMockMvc(addFilters = false)

class CreateOrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemMapper orderItemMapper;

    @MockBean
    private CreateOrderItemUseCase createOrderItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldCreateOrderItemSuccessfully() throws Exception {
        // given (entrada do cliente)
        OrderItemRequestDTO requestDTO = createRequestDTOWithId();

        OrderItem domain = createDefaultWithId();

        OrderItemResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(orderItemMapper.toDomain(any(OrderItemRequestDTO.class))).thenReturn(domain);
        Mockito.when(createOrderItemUseCase.execute(any(OrderItem.class))).thenReturn(domain);
        Mockito.when(orderItemMapper.toResponseDTO(any(OrderItem.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/orderitems")
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)).header(
                                "Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo"
                        ))


                .andExpect(header().string("Location", "/orderitems/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.pedido_id").value(1))
                .andExpect(jsonPath("$.menu_item_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));

        // verificação de chamadas
        Mockito.verify(orderItemMapper).toDomain(requestDTO);
        Mockito.verify(createOrderItemUseCase).execute(domain);
        Mockito.verify(orderItemMapper).toResponseDTO(domain);
    }
}
