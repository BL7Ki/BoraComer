package pos.java.bora_comer.infra.delivery.order;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatus;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createUpdateRequestDTOWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;

@WebMvcTest(controllers = UpdateOrderController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UpdateOrderControllerTest {

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
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldUpdateOrderSuccessfully() throws Exception {
        Long id = 10L;
        Long restauranteId = 1L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        OrderStatus status = OrderStatus.PENDING;

        OrderUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        Order existingOrder = Order.create(
                id,
                dateTime,
                true,
                restauranteId,
                userId,
                dateTime,
                status
        );

        Mockito.when(updateOrderUseCase.findById(id)).thenReturn(existingOrder);

        Order domainOrder = Order.create(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                updateRequestDTO.userId(),
                updateRequestDTO.restaurantId(),
                updateRequestDTO.lastModifiedDate(),
                updateRequestDTO.status()
        );

        Mockito.when(orderMapper.toDomain(updateRequestDTO, id, restauranteId, userId)).thenReturn(domainOrder);
        Mockito.when(updateOrderUseCase.execute(domainOrder)).thenReturn(domainOrder);

        OrderResponseDTO responseDTO = new OrderResponseDTO(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                userId,
                restauranteId,
                updateRequestDTO.lastModifiedDate(),
                updateRequestDTO.status()
        );

        Mockito.when(orderMapper.toResponseDTO(domainOrder)).thenReturn(responseDTO);

        mockMvc.perform(put("/orders/{id}", id)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.delivery").value(updateRequestDTO.delivery()))
                .andExpect(jsonPath("$.restaurante_id").value(restauranteId))
                .andExpect(jsonPath("$.usuario_id").value(userId));

    }
}
