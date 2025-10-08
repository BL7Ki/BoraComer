package pos.java.bora_comer.infra.delivery.menu;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.menu.DeleteMenuItemUseCase;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = DeleteMenuItemController.class)
@AutoConfigureMockMvc(addFilters = false)

public class DeleteMenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldDeleteMenuItemSuccessfully() throws Exception {
        Long menuItemId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deleteMenuItemUseCase).execute(menuItemId);

        mockMvc.perform(delete("/menu-items/{id}", menuItemId)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteMenuItemUseCase, Mockito.times(1)).execute(menuItemId);
    }
}
