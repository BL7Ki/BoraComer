package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;
import pos.java.bora_comer.util.UserTestFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

class UppdateUserUseCaseImplTest {

    private UserUpdateGateway userUpdateGateway;
    private UserSearchGateway userSearchGateway;
    private UppdateUserUseCaseImpl uppdateUserUseCase;
    Long id = 2L;

    @BeforeEach
    void setUp() {
        userUpdateGateway = mock(UserUpdateGateway.class);
        userSearchGateway = mock(UserSearchGateway.class);
        uppdateUserUseCase = new UppdateUserUseCaseImpl(userUpdateGateway, userSearchGateway);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() throws UserDomainException {
        Address someAddress = mock(Address.class);

        User user = UserTestFactory.umUserAtualizado(id);

        when(userUpdateGateway.update(user)).thenReturn(user);

        User result = uppdateUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveLancarUserDomainException_quandoUpdateGatewayLancarIllegalArgumentException() {
        Address someAddress = mock(Address.class);

        User user = UserTestFactory.umUserAtualizado(id);

        when(userUpdateGateway.update(user))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        UserDomainException exception = assertThrows(UserDomainException.class, () -> {
            uppdateUserUseCase.execute(user);
        });

        assertEquals("Dados inválidos", exception.getMessage());
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveTrocarSenhaComSucesso() throws UserDomainException {
        Address address = mock(Address.class);
        User user = UserTestFactory.umUserAtualizado(id);

        when(userSearchGateway.findById(2L)).thenReturn(Optional.of(user));
        when(userUpdateGateway.update(user)).thenReturn(user);

        uppdateUserUseCase.changeUserPassword(2L, "NovaSenha@123", "NovaSenha@1234");

        assertEquals("NovaSenha@1234", user.getPassword());
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(userSearchGateway.findById(2L)).thenReturn(Optional.empty());

        UserDomainException ex = assertThrows(UserDomainException.class, () ->
                uppdateUserUseCase.changeUserPassword(2L, "qualquer", "novaSenha")
        );
        assertEquals("Usuário não encontrado.", ex.getMessage());
        verify(userUpdateGateway, never()).update(any());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualIncorreta() {
        Address address = mock(Address.class);
        User user = UserTestFactory.umUserAtualizado(id);

        when(userSearchGateway.findById(2L)).thenReturn(Optional.of(user));

        UserDomainException ex = assertThrows(UserDomainException.class, () ->
                uppdateUserUseCase.changeUserPassword(2L, "senhaErrada", "novaSenha")
        );
        assertEquals("Senha atual incorreta.", ex.getMessage());
        verify(userUpdateGateway, never()).update(any());
    }
}
