package pos.java.bora_comer.infra.delivery.userType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.CustomExceptionHandler;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;
import pos.java.bora_comer.util.factory.UserTypeFactory;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class SearchUserTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SearchUserTypeUseCase searchUserTypeUseCase;

    @Mock
    private UserTypeMapper userTypeMapper;

    @InjectMocks
    private SearchUserTypeController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void deveBuscarTodosUserTypesComSucesso() throws Exception {
        int page = 0;
        int size = 2;
        List<UserType> userTypes = List.of(
                UserTypeFactory.createUserType(),
                UserTypeFactory.createUserType()
        );
        Page<UserType> pageResult = new PageImpl<>(userTypes, PageRequest.of(page, size), userTypes.size());

        UserTypeResponseDTO dto1 = new UserTypeResponseDTO(1L, "DONO_RESTAURANTE");
        UserTypeResponseDTO dto2 = new UserTypeResponseDTO(2L, "CLIENTE");

        when(searchUserTypeUseCase.findAll(page, size)).thenReturn(pageResult);
        when(userTypeMapper.toResponse(userTypes.get(0))).thenReturn(dto1);
        when(userTypeMapper.toResponse(userTypes.get(1))).thenReturn(dto2);

        mockMvc.perform(get("/user-types/search")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].tipo_usuario").value("DONO_RESTAURANTE"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].tipo_usuario").value("CLIENTE"));
    }

    @Test
    void deveRetornarErroQuandoGatewayLancarExcecao() throws Exception {
        int page = 0;
        int size = 2;

        when(searchUserTypeUseCase.findAll(page, size))
                .thenThrow(new UserDomainException("Erro ao buscar user types"));

        mockMvc.perform(get("/user-types/search")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Erro ao buscar user types"))
                .andExpect(jsonPath("$.path").value("/user-types/search"));
    }

}