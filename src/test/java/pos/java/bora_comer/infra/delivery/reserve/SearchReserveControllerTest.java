package pos.java.bora_comer.infra.delivery.reserve;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.SearchReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createResponseDTOWithId;

@WebMvcTest(
        controllers = SearchReserveController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class SearchReserveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchReserveUseCase searchReserveUseCase;

    @MockBean
    private ReserveMapper reserveMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldFindReserveByIdSuccessfully() throws Exception {
        Long id = 10L;

        Reserve mockReserve = createDefaultWithId();

        ReserveResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchReserveUseCase.findById(id)).thenReturn(mockReserve);
        Mockito.when(reserveMapper.toResponseDTO(mockReserve)).thenReturn(responseDTO);

        mockMvc.perform(get("/reserves/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.usuario_id").value(1))
                .andExpect(jsonPath("$.restaurante_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));
        Mockito.verify(searchReserveUseCase, Mockito.times(1)).findById(id);
        Mockito.verify(reserveMapper, Mockito.times(1)).toResponseDTO(mockReserve);
    }

    @Test
    void shouldFindAllReserveSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        Reserve reserve1 = createDefaultWithId();

        Reserve reserve2 = createDefaultWithId();

        Page<Reserve> pageResult = new PageImpl<>(List.of(reserve1, reserve2), PageRequest.of(page, size), 2);

        ReserveResponseDTO dto1 = createResponseDTOWithId();

        ReserveResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchReserveUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(reserveMapper.toResponseDTO(reserve1)).thenReturn(dto1);
        Mockito.when(reserveMapper.toResponseDTO(reserve2)).thenReturn(dto2);

        mockMvc.perform(get("/reserves")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].restaurante_id").value(1L))
                .andExpect(jsonPath("$[0].usuario_id").value(1L))
                .andExpect(jsonPath("$[0].quantidade").value(2))
                .andExpect(jsonPath("$[0].data_hora").value("2024-10-10T12:00:00"))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].restaurante_id").value(1L))
                .andExpect(jsonPath("$[1].usuario_id").value(1L))
                .andExpect(jsonPath("$[1].quantidade").value(2))
                .andExpect(jsonPath("$[1].data_hora").value("2024-10-10T12:00:00"));
                
    }
}
