package pos.java.bora_comer.infra.delivery.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.factory.UserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchUserControllerTest {

    @Mock
    private SearchUserUseCase searchUserUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SearchUserController controller;

    @Test
    void deveriaBuscarUsuarioPorIdERetornar200() {
        // Arrange

        User domainUser = UserFactory.umUserPadrao();

        UserResponseDTO responseDTO = new UserResponseDTO(
                domainUser.getId(),
                "Leo Messi",
                "messi@email.com",
                "messi10",
                new AddressResponseDTO("Rua A", "123", "São Paulo", "SP", "01234-567"),
                "CLIENTE",
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700"
        );

        when(searchUserUseCase.findById(domainUser.getId())).thenReturn(domainUser);
        when(userMapper.toResponseDTO(domainUser)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = controller.findById(domainUser.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(searchUserUseCase).findById(domainUser.getId());
        verify(userMapper).toResponseDTO(domainUser);
    }

    @Test
    void deveriaBuscarTodosUsuariosComPaginacaoERetornar200() {
        // Arrange
        int page = 0;
        int size = 2;

        User user1 = UserFactory.umUserComIdRandomico();

        User user2 = UserFactory.umUserComIdRandomico();

        List<User> userList = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(userList);

        UserResponseDTO responseDTO1 = new UserResponseDTO(
                1L,
                "Leo Messi",
                "messi@email.com",
                "messi10",
                new AddressResponseDTO("Rua A", "123", "São Paulo", "SP", "01234-567"),
                "CLIENTE",
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700"
        );

        UserResponseDTO responseDTO2 = new UserResponseDTO(
                2L,
                "Cristiano Ronaldo",
                "cr7@email.com",
                "cristiano7",
                new AddressResponseDTO("Rua A", "123", "São Paulo", "SP", "01234-567"),
                "ADMIN",
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700"
        );

        when(searchUserUseCase.findAll(page, size)).thenReturn(userPage);
        when(userMapper.toResponseDTO(user1)).thenReturn(responseDTO1);
        when(userMapper.toResponseDTO(user2)).thenReturn(responseDTO2);

        // Act
        ResponseEntity<List<UserResponseDTO>> response = controller.findAll(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserResponseDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertTrue(body.contains(responseDTO1));
        assertTrue(body.contains(responseDTO2));

        verify(searchUserUseCase).findAll(page, size);
        verify(userMapper).toResponseDTO(user1);
        verify(userMapper).toResponseDTO(user2);
    }
}
