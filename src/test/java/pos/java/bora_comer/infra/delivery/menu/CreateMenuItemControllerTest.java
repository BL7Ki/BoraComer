package pos.java.bora_comer.infra.delivery.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.*; // Presumindo que você usa isso

@WebMvcTest(
        controllers = CreateMenuItemController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@AutoConfigureMockMvc(addFilters = false)
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
        // given
        MenuItemRequestDTO requestDTO = new MenuItemRequestDTO(
                "Sushi",
                "Sushi de salmão com arroz",
                new BigDecimal("29.99"),
                true,
                "sushi.jpg",
                1L
        );

        MenuItem domainWithoutId = MenuItem.create(
                "Sushi",
                "Sushi de salmão com arroz",
                new BigDecimal("29.99"),
                true,
                "sushi.jpg",
                1L
        );

        Long createdId = 10L;
        MenuItem createdDomainWithId = MenuItem.create(
                createdId,
                "Sushi",
                "Sushi de salmão com arroz",
                new BigDecimal("29.99"),
                true,
                "sushi.jpg",
                1L
        );

        MenuItemResponseDTO responseDTO = new MenuItemResponseDTO(
                createdId,
                "Sushi",
                "Sushi de salmão com arroz",
                new BigDecimal("29.99"),
                true,
                "sushi.jpg",
                1L
        );


        // mocks
        // Mapeamento de RequestDTO -> Domain (sem ID)
        Mockito.when(menuItemMapper.toDomain(requestDTO)).thenReturn(domainWithoutId);

        // Execução do UseCase (Domain sem ID -> Domain com ID)
        Mockito.when(createMenuItemUseCase.execute(domainWithoutId)).thenReturn(createdDomainWithId);

        // Mapeamento de Domain (com ID) -> ResponseDTO
        Mockito.when(menuItemMapper.toResponseDTO(createdDomainWithId)).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/menu-items")
                        .header("Authorization", "Bearer token-valido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/menu-items/" + createdId))
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.nome").value(responseDTO.name()))
                .andExpect(jsonPath("$.descricao").value(responseDTO.description()))
                .andExpect(jsonPath("$.preco").value(responseDTO.price()))
                .andExpect(jsonPath("$.delivery").value(responseDTO.delivery()));


        // verify
        Mockito.verify(menuItemMapper).toDomain(requestDTO);
        Mockito.verify(createMenuItemUseCase).execute(domainWithoutId);
        Mockito.verify(menuItemMapper).toResponseDTO(createdDomainWithId);
    }
}