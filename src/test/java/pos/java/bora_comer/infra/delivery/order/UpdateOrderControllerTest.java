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
import pos.java.bora_comer.core.domain.order.OrderStatus;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createUpdateRequestDTOWithId;

@WebMvcTest(controllers = UpdateOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private UpdateOrderUseCase updateOrderUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldUpdateOrderSuccessfully() throws Exception {
        // given
        Long id = 10L;
        Long restauranteId = 1L;
        Long userId = 1L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");
        OrderStatus status = OrderStatus.PENDING;

        OrderUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        // domínio simulado (sem status vindo do DTO)
        Order domainOrder = Order.create(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                updateRequestDTO.restaurantId(),
                updateRequestDTO.userId(),
                updateRequestDTO.lastModifiedDate(),
                status // define manualmente o status no teste
        );

        OrderResponseDTO responseDTO = new OrderResponseDTO(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                userId,
                restauranteId,
                updateRequestDTO.lastModifiedDate(),
                status
        );

        // mocks
        Mockito.when(orderMapper.toDomain(any(OrderUpdateRequestDTO.class), any(Long.class), any(Long.class), any(Long.class)))
                .thenReturn(domainOrder);
        Mockito.when(updateOrderUseCase.execute(domainOrder)).thenReturn(domainOrder);
        Mockito.when(orderMapper.toResponseDTO(domainOrder)).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(put("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.delivery").value(updateRequestDTO.delivery()))
                .andExpect(jsonPath("$.restaurante_id").value(restauranteId))
                .andExpect(jsonPath("$.usuario_id").value(userId));
    }
}
