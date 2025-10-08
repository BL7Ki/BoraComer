package pos.java.bora_comer.infra.delivery.user;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.util.factory.UserTestFactory;

@ExtendWith(MockitoExtension.class)
class CreateUserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @InjectMocks
    private CreateUserController controller;

    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

    @Test
    void deveriaCriarNovoUsuarioERetornar201() {
        // Arrange: cria os DTOs e objetos simulados

        UserRequestDTO requestDTO = UserTestFactory.createUserRequestDTO();

        User domainUser = UserTestFactory.umUserPadrao();

        UserResponseDTO responseDTO = UserTestFactory.createUserResponseDTO();

        // Configura os mocks
        when(userMapper.toDomain(requestDTO)).thenReturn(domainUser);
        when(createUserUseCase.execute(domainUser)).thenReturn(domainUser);
        when(userMapper.toResponseDTO(domainUser)).thenReturn(responseDTO);

        // Act: executa o método da controller
        ResponseEntity<UserResponseDTO> response = controller.create(autorizationHeader, requestDTO);

        // Assert: valida status, body e location
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(responseDTO, response.getBody());
        assertEquals("/users/" + responseDTO.id(), Objects.requireNonNull(response.getHeaders().getLocation()).toString());

        // Verifica interações
        verify(userMapper).toDomain(requestDTO);
        verify(createUserUseCase).execute(domainUser);
        verify(userMapper).toResponseDTO(domainUser);
    }
}
