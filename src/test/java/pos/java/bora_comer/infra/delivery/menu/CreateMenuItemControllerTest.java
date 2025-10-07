package pos.java.bora_comer.infra.delivery.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.CreateMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.*;

@WebMvcTest(
        controllers = CreateMenuItemController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class CreateMenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuItemMapper menuItemMapper;

    @MockBean
    private CreateMenuItemUseCase createMenuItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldCreateMenuItemSuccessfully() throws Exception {
        // given (entrada do cliente)
        MenuItemRequestDTO requestDTO = createRequestDTOWithId();

        MenuItem domain = createDefaultWithId();

        MenuItemResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(menuItemMapper.toDomain(any(MenuItemRequestDTO.class))).thenReturn(domain);
        Mockito.when(createMenuItemUseCase.execute(any(MenuItem.class))).thenReturn(domain);
        Mockito.when(menuItemMapper.toResponseDTO(any(MenuItem.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/menu-items/10"))
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Sushi"))
                .andExpect(jsonPath("$.descricao").value("Sushi de salmão com arroz"))
                .andExpect(jsonPath("$.preco").value(29.99))
                .andExpect(jsonPath("$.delivery").value(true))
                .andExpect(jsonPath("$.imagem_caminho").value("sushi.jpg"))
                .andExpect(jsonPath("$.restaurante_id").value(1L));
                
        // verificação de chamadas
        Mockito.verify(menuItemMapper).toDomain(requestDTO);
        Mockito.verify(createMenuItemUseCase).execute(domain);
        Mockito.verify(menuItemMapper).toResponseDTO(domain);
    }
}
