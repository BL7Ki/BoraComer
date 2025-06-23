package pos.java.bora_comer.infra.delivery.user;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.AddressRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserRoleRequestEnumDTO;

@ExtendWith(MockitoExtension.class)
class CreateUserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @InjectMocks
    private CreateUserController controller;

    @Test
    void deveriaCriarNovoUsuarioERetornar201() {
        // Arrange: cria os DTOs e objetos simulados
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                "Rua A", "123", "São Paulo", "SP", "01234-567"
        );

        AddressResponseDTO addressResponse = new AddressResponseDTO(
                "Rua A", "123", "São Paulo", "SP", "01234-567"
        );

        UserRoleRequestEnumDTO userType = UserRoleRequestEnumDTO.CLIENTE;

        UserRequestDTO requestDTO = new UserRequestDTO(
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                "senha123",
                addressRequest,
                userType
        );

        // Mock de Address (ou poderia criar real se Address.create() existir)
        Address address = mock(Address.class);

        User domainUser = User.create(
                1L,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                "senha123",
                address,
                UserRoleEnum.CLIENTE,
                "2024-06-21T12:00:00"
        );

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                addressResponse,
                "CLIENTE",
                "2024-06-21T12:00:00"
        );

        // Configura os mocks
        when(userMapper.toDomain(requestDTO)).thenReturn(domainUser);
        when(createUserUseCase.execute(domainUser)).thenReturn(domainUser);
        when(userMapper.toResponseDTO(domainUser)).thenReturn(responseDTO);

        // Act: executa o método da controller
        ResponseEntity<UserResponseDTO> response = controller.create(requestDTO);

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
