package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchUserUseCaseImplTest {

    private UserSearchGateway userSearchGateway;
    private SearchUserUseCaseImpl searchUserUseCase;
    private final String TEST_USERNAME = "messi";

    @BeforeEach
    void setUp() {
        userSearchGateway = mock(UserSearchGateway.class);
        searchUserUseCase = new SearchUserUseCaseImpl(userSearchGateway);
    }

    @Test
    void findByUsername_deveRetornarUsuario_quandoExistir() throws SummerNotFoundException {
        // Arrange
        User user = UserTestFactory.umUserPadrao(); // Retorna o usuário com username "messi"

        when(userSearchGateway.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

        // Act
        User result = searchUserUseCase.findByUsername(TEST_USERNAME);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userSearchGateway).findByUsername(TEST_USERNAME);
    }

    @Test
    void findByUsername_deveLancarExcecao_quandoNaoExistir() {
        // Arrange
        String nonExistentUsername = "naoexiste";
        when(userSearchGateway.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        // Act & Assert
        SummerNotFoundException exception = assertThrows(SummerNotFoundException.class,
                () -> searchUserUseCase.findByUsername(nonExistentUsername));

        // Ajuste da mensagem para refletir a busca por username
        assertEquals("User with username naoexiste not found", exception.getMessage());
        verify(userSearchGateway).findByUsername(nonExistentUsername);
    }

    @Test
    void findAll_deveRetornarPaginaDeUsuarios() throws UserDomainException {
        // Arrange
        User user1 = UserTestFactory.umUserPadrao();
        User user2 = UserTestFactory.umUserPadrao();

        List<User> users = List.of(user1, user2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userSearchGateway.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        Page<User> result = searchUserUseCase.findAll(0, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(users, result.getContent());

        // Verificar se o Pageable passado tem as mesmas propriedades
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(userSearchGateway).findAll(pageableCaptor.capture());
        Pageable capturado = pageableCaptor.getValue();

        assertEquals(0, capturado.getPageNumber());
        assertEquals(2, capturado.getPageSize());
    }
}