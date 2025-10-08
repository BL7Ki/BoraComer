package pos.java.bora_comer.infra.delivery.userType;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;
import pos.java.bora_comer.util.factory.UserTypeFactory;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = SearchUserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
class SearchUserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchUserTypeUseCase searchUserTypeUseCase;

    @MockBean
    private UserTypeMapper userTypeMapper;

    @InjectMocks
    private SearchUserTypeController controller;


    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";


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
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
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
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Erro ao buscar user types"))
                .andExpect(jsonPath("$.path").value("/user-types/search"));
    }

}