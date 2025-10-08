package pos.java.bora_comer.infra.delivery.menu;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.SearchMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createResponseDTOWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest( controllers = SearchMenuItemController.class)
@AutoConfigureMockMvc(addFilters = false)

public class SearchMenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchMenuItemUseCase searchMenuItemUseCase;

    @MockBean
    private MenuItemMapper menuItemMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldFindMenuItemByIdSuccessfully() throws Exception {
        Long id = 10L;

        MenuItem mockMenuItem = createDefaultWithId();

        MenuItemResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchMenuItemUseCase.findById(id)).thenReturn(mockMenuItem);
        Mockito.when(menuItemMapper.toResponseDTO(mockMenuItem)).thenReturn(responseDTO);

        mockMvc.perform(get("/menu-items/{id}", id)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Sushi"))
                .andExpect(jsonPath("$.descricao").value("Sushi de salmão com arroz"))
                .andExpect(jsonPath("$.preco").value(29.99))
                .andExpect(jsonPath("$.delivery").value(true))
                .andExpect(jsonPath("$.imagem_caminho").value("sushi.jpg"))
                .andExpect(jsonPath("$.restaurante_id").value(1L));
    }

    @Test
    void shouldFindAllMenuItemsSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        MenuItem menuItem1 = createDefaultWithId();

        MenuItem menuItem2 = createDefaultWithId();

        Page<MenuItem> pageResult = new PageImpl<>(List.of(menuItem1, menuItem2), PageRequest.of(page, size), 2);

        MenuItemResponseDTO dto1 = createResponseDTOWithId();

        MenuItemResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchMenuItemUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(menuItemMapper.toResponseDTO(menuItem1)).thenReturn(dto1);
        Mockito.when(menuItemMapper.toResponseDTO(menuItem2)).thenReturn(dto2);

        mockMvc.perform(get("/menu-items")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].nome").value("Sushi"))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].nome").value("Sushi"));
    }
}
