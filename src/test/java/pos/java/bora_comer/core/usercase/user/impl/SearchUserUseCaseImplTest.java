package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;

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

    @BeforeEach
    void setUp() {
        userSearchGateway = mock(UserSearchGateway.class);
        searchUserUseCase = new SearchUserUseCaseImpl(userSearchGateway);
    }

    @Test
    void findById_deveRetornarUsuario_quandoExistir() throws SummerNotFoundException {
        Address someAddress = mock(Address.class);
        User user = User.create(
                1L,
                "Lionel Messi",
                "messi@example.com",
                "messi",
                "Messi@123",
                someAddress,
                UserRoleEnum.CLIENTE,
                "2025-06-24"
        );

        when(userSearchGateway.findById(1L)).thenReturn(Optional.of(user));

        User result = searchUserUseCase.findById(1L);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userSearchGateway).findById(1L);
    }

    @Test
    void findById_deveLancarExcecao_quandoNaoExistir() {
        when(userSearchGateway.findById(99L)).thenReturn(Optional.empty());

        SummerNotFoundException exception = assertThrows(SummerNotFoundException.class, () -> {
            searchUserUseCase.findById(99L);
        });

        assertEquals("User with ID 99 not found", exception.getMessage());
        verify(userSearchGateway).findById(99L);
    }

    @Test
    void findAll_deveRetornarPaginaDeUsuarios() throws UserDomainException {
        Address someAddress = mock(Address.class);
        User user1 = User.create(
                1L,
                "Lionel Messi",
                "messi@example.com",
                "messi",
                "Messi@123",
                someAddress,
                UserRoleEnum.CLIENTE,
                "2025-06-24"
        );
        User user2 = User.create(
                2L,
                "Cristiano Ronaldo",
                "cr7@example.com",
                "cr7",
                "CR7@123",
                someAddress,
                UserRoleEnum.ADMIN,
                "2025-06-24"
        );

        List<User> users = List.of(user1, user2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userSearchGateway.findAll(any(Pageable.class))).thenReturn(page);

        Page<User> result = searchUserUseCase.findAll(0, 2);

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
