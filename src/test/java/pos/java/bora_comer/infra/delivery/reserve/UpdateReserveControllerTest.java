package pos.java.bora_comer.infra.delivery.reserve;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.UpdateReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createUpdateRequestDTOWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;

@WebMvcTest(controllers = UpdateReserveController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UpdateReserveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReserveMapper reserveMapper;

    @MockBean
    private UpdateReserveUseCase updateReserveUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldUpdateReserveSuccessfully() throws Exception {
        Long id = 10L;
        Long restauranteId = 1L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        ReserveUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        Reserve existingReserve = Reserve.create(
                id,
                dateTime,
                2,
                restauranteId,
                userId,
                dateTime
        );

        Mockito.when(updateReserveUseCase.findById(id)).thenReturn(existingReserve);

        Reserve domainReserve= Reserve.create(
                id,
                updateRequestDTO.dateTimeReserve(),
                updateRequestDTO.quantity(),
                updateRequestDTO.userId(),
                updateRequestDTO.restaurantId(),
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(reserveMapper.toDomain(updateRequestDTO, id, restauranteId, userId)).thenReturn(domainReserve);
        Mockito.when(updateReserveUseCase.execute(domainReserve)).thenReturn(domainReserve);

        ReserveResponseDTO responseDTO = new ReserveResponseDTO(
                id,
                updateRequestDTO.dateTimeReserve(),
                updateRequestDTO.quantity(),
                userId,
                restauranteId,
                updateRequestDTO.lastModifiedDate()
        );

        Mockito.when(reserveMapper.toResponseDTO(domainReserve)).thenReturn(responseDTO);

        mockMvc.perform(put("/reserves/{id}", id)
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.quantidade").value(updateRequestDTO.quantity()))
                .andExpect(jsonPath("$.restaurante_id").value(restauranteId))
                .andExpect(jsonPath("$.usuario_id").value(userId))
                .andExpect(jsonPath("$.data_hora").value(dateStr));
    }
}
