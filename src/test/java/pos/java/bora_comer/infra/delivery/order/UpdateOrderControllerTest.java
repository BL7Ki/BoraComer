package pos.java.bora_comer.infra.delivery.order;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createUpdateRequestDTOWithId;

import java.time.LocalDateTime;

@WebMvcTest(UpdateOrderController.class)
public class UpdateOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private UpdateOrderUseCase updateOrderUseCase;

    @Test
    void shouldUpdateOrderSuccessfully() throws Exception {
        Long id = 10L;
        Long restauranteId = 1L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        Order existingOrder = Order.create(  
                id,
                dateTime,
                true,
                restauranteId,
                userId,
                dateTime
        );

        Mockito.when(updateOrderUseCase.findById(id)).thenReturn(existingOrder);

        Order domainOrder = Order.create(    
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                updateRequestDTO.userId(),
                updateRequestDTO.restaurantId(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(orderMapper.toDomain(updateRequestDTO, id, restauranteId, userId)).thenReturn(domainOrder);
        Mockito.when(updateOrderUseCase.execute(domainOrder)).thenReturn(domainOrder);

        OrderResponseDTO responseDTO = new OrderResponseDTO(
                id,
                updateRequestDTO.dateTimeOrder(),
                updateRequestDTO.delivery(),
                userId,
                restauranteId,
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(orderMapper.toResponseDTO(domainOrder)).thenReturn(responseDTO);

        mockMvc.perform(put("/orders/{id}", id)
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
