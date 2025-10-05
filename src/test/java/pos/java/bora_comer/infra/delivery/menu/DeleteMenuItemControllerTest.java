package pos.java.bora_comer.infra.delivery.menu;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.menu.DeleteMenuItemUseCase;
import pos.java.bora_comer.infra.service.CustomUserDetailsService;
import pos.java.bora_comer.infra.service.JwtService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = DeleteMenuItemController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class
        }
)
public class DeleteMenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @MockBean
    private JwtService jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldDeleteMenuItemSuccessfully() throws Exception {
        Long menuItemId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deleteMenuItemUseCase).execute(menuItemId);

        mockMvc.perform(delete("/menu-items/{id}", menuItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deleteMenuItemUseCase, Mockito.times(1)).execute(menuItemId);
    }
}
