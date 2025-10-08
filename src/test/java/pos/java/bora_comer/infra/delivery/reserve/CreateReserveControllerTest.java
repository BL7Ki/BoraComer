package pos.java.bora_comer.infra.delivery.reserve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.core.usercase.reserve.CreateReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createRequestDTOWithId;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefaultWithId;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CreateReserveController.class)
@AutoConfigureMockMvc(addFilters = false)

class CreateReserveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReserveMapper reserveMapper;

    @MockBean
    private CreateReserveUseCase createReserveUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void shouldCreateReserveSuccessfully() throws Exception {
        // given (entrada do cliente)
        ReserveRequestDTO requestDTO = createRequestDTOWithId();

        Reserve domain = createDefaultWithId();

        ReserveResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(reserveMapper.toDomain(any(ReserveRequestDTO.class))).thenReturn(domain);
        Mockito.when(createReserveUseCase.execute(any(Reserve.class))).thenReturn(domain);
        Mockito.when(reserveMapper.toResponseDTO(any(Reserve.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/reserves")
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reserves/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.usuario_id").value(1))
                .andExpect(jsonPath("$.restaurante_id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2))
                .andExpect(jsonPath("$.data_hora").value("2024-10-10T12:00:00"));                ;


        // verificação de chamadas
        Mockito.verify(reserveMapper).toDomain(requestDTO);
        Mockito.verify(createReserveUseCase).execute(domain);
        Mockito.verify(reserveMapper).toResponseDTO(domain);
    }
}
