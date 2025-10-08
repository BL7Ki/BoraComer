package pos.java.bora_comer.infra.delivery.orderItem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.orderItem.DeleteOrderItemUseCase;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = DeleteOrderItemController.class)
@AutoConfigureMockMvc(addFilters = false)

public class DeleteOrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteOrderItemUseCase deleteOrderItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldDeleteOrderItemSuccessfully() throws Exception {
        Long orderItemId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deleteOrderItemUseCase).execute(orderItemId);

        mockMvc.perform(delete("/orderitems/{id}", orderItemId)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deleteOrderItemUseCase, Mockito.times(1)).execute(orderItemId);
    }
}
