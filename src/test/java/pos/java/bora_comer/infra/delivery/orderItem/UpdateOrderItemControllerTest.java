package pos.java.bora_comer.infra.delivery.orderItem;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.UpdateOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;

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
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createUpdateRequestDTOWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;

@WebMvcTest(controllers = UpdateOrderItemController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UpdateOrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemMapper orderItemMapper;

    @MockBean
    private UpdateOrderItemUseCase updateOrderItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldUpdateOrderItemSuccessfully() throws Exception {
        Long id = 10L;
        Long orderId = 1L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderItemUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        OrderItem existingOrderItem = OrderItem.create(
                id,
                orderId,
                menuItemId,
                2,
                dateTime
        );

        Mockito.when(updateOrderItemUseCase.findById(id)).thenReturn(existingOrderItem);

        OrderItem domainOrderItem = OrderItem.create(
                id,
                orderId,
                menuItemId,
                updateRequestDTO.quantity(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(orderItemMapper.toDomain(updateRequestDTO, id, orderId, menuItemId)).thenReturn(domainOrderItem);
        Mockito.when(updateOrderItemUseCase.execute(domainOrderItem)).thenReturn(domainOrderItem);

        OrderItemResponseDTO responseDTO = new OrderItemResponseDTO(
                id,
                orderId,
                menuItemId,
                updateRequestDTO.quantity(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(orderItemMapper.toResponseDTO(domainOrderItem)).thenReturn(responseDTO);

        mockMvc.perform(put("/orderitems/{id}", id)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.pedido_id").value(orderId))
                .andExpect(jsonPath("$.menu_item_id").value(menuItemId))
                .andExpect(jsonPath("$.quantidade").value(updateRequestDTO.quantity()));
    }
}
