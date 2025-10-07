package pos.java.bora_comer.infra.delivery.orderItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.core.usercase.orderItem.CreateOrderItemUseCase;
import pos.java.bora_comer.infra.delivery.order.CreateOrderController;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createRequestDTOWithId;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;

@WebMvcTest(
        controllers = CreateOrderItemController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
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
