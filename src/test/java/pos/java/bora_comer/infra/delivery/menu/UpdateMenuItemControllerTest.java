package pos.java.bora_comer.infra.delivery.menu;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.core.usercase.menu.UpdateMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createUpdateRequestDTOWithId;

import java.math.BigDecimal;

@WebMvcTest(UpdateMenuItemController.class)
public class UpdateMenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuItemMapper menuItemMapper;

    @MockBean
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Test
    void shouldUpdateMenuItemSuccessfully() throws Exception {
        Long id = 10L;
        Long restauranteId = 1L;

        MenuItemUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        MenuItem existingMenuItem = MenuItem.create(  
                id,
                "Old Name",
                "Old Description",
                 new BigDecimal("19.99"),
                false,
                "old_image_path.jpg",   
                restauranteId
        );

        Mockito.when(updateMenuItemUseCase.findById(id)).thenReturn(existingMenuItem);

        MenuItem domainMenuItem = MenuItem.create(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.description(),
                updateRequestDTO.price(),
                updateRequestDTO.inPlaceOnly(),
                updateRequestDTO.imagePath(),
                restauranteId
        );

        Mockito.when(menuItemMapper.toDomain(updateRequestDTO, id, restauranteId)).thenReturn(domainMenuItem);
        Mockito.when(updateMenuItemUseCase.execute(domainMenuItem)).thenReturn(domainMenuItem);

        MenuItemResponseDTO responseDTO = new MenuItemResponseDTO(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.description(),
                updateRequestDTO.price(),
                updateRequestDTO.inPlaceOnly(),
                updateRequestDTO.imagePath(),
                restauranteId   
        );

        Mockito.when(menuItemMapper.toResponseDTO(domainMenuItem)).thenReturn(responseDTO);

        mockMvc.perform(put("/menu-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(updateRequestDTO.name()))   
                .andExpect(jsonPath("$.descricao").value(updateRequestDTO.description()))
                .andExpect(jsonPath("$.preco").value(updateRequestDTO.price().doubleValue()))
                .andExpect(jsonPath("$.so_no_local").value(updateRequestDTO.inPlaceOnly()))
                .andExpect(jsonPath("$.imagem_caminho").value(updateRequestDTO.imagePath()))
                .andExpect(jsonPath("$.restaurante_id").value(restauranteId));
    }
}
