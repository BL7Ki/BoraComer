package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

class UpdateUserUseCaseImplTest {

    private UserUpdateGateway userUpdateGateway;
    private UserSearchGateway userSearchGateway;
    private PasswordEncoder passwordEncoder;
    private UpdateUserUseCaseImpl uppdateUserUseCase;

    String username = "testUser";

    @BeforeEach
    void setUp() {
        userUpdateGateway = mock(UserUpdateGateway.class);
        userSearchGateway = mock(UserSearchGateway.class);
        passwordEncoder = mock(PasswordEncoder.class);
        uppdateUserUseCase = new UpdateUserUseCaseImpl(userUpdateGateway, userSearchGateway, passwordEncoder);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() throws UserDomainException {
        Long id = 2L;
        User user = UserTestFactory.umUserAtualizado(id);

        when(userUpdateGateway.update(user)).thenReturn(user);

        User result = uppdateUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveLancarUserDomainException_quandoUpdateGatewayLancarIllegalArgumentException() {
        Long id = 2L;
        User user = UserTestFactory.umUserAtualizado(id);

        when(userUpdateGateway.update(user))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        UserDomainException exception = assertThrows(UserDomainException.class, () -> uppdateUserUseCase.execute(user));

        assertEquals("Dados inválidos", exception.getMessage());
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveTrocarSenhaComSucesso() throws UserDomainException {
        // ARRANGE
        String currentRawPassword = "SenhaAntiga123";
        String newRawPassword = "NovaSenhaForte@123";
        String encodedOldPassword = "hash_da_senha_antiga";
        String encodedNewPassword = "hash_da_nova_senha";

        User user = UserTestFactory.umUserPadrao();
        user.updatePassword(encodedOldPassword);

        when(userSearchGateway.findByUsername(username)).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(currentRawPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.encode(newRawPassword)).thenReturn(encodedNewPassword);
        when(userUpdateGateway.update(user)).thenReturn(user);

        // ACT
        uppdateUserUseCase.changeUserPassword(username, currentRawPassword, newRawPassword);

        // ASSERT
        assertEquals(encodedNewPassword, user.getPassword());

        verify(passwordEncoder).matches(currentRawPassword, encodedOldPassword);
        verify(passwordEncoder).encode(newRawPassword);
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(userSearchGateway.findByUsername(username)).thenReturn(Optional.empty());

        UserDomainException ex = assertThrows(UserDomainException.class, () ->
                uppdateUserUseCase.changeUserPassword(username, "qualquer", "novaSenha")
        );
        assertEquals("Usuário não encontrado.", ex.getMessage());
        verify(userUpdateGateway, never()).update(any());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualIncorreta() {
        // ARRANGE
        String currentRawPassword = "senhaErrada";
        String encodedOldPassword = "hash_da_senha_correta";

        User user = UserTestFactory.umUserPadrao();
        user.updatePassword(encodedOldPassword);

        when(userSearchGateway.findByUsername(username)).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(currentRawPassword, encodedOldPassword)).thenReturn(false);

        // ACT & ASSERT
        UserDomainException ex = assertThrows(UserDomainException.class, () ->
                uppdateUserUseCase.changeUserPassword(username, currentRawPassword, "novaSenha")
        );

        assertEquals("Senha atual incorreta.", ex.getMessage());

        verify(passwordEncoder).matches(currentRawPassword, encodedOldPassword);
        verify(userUpdateGateway, never()).update(any());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void deveAssociarTipoUsuarioComSucesso() throws UserDomainException {
        Long userId = 10L;
        Long userTypeId = 20L;
        User userReturned = UserTestFactory.umUserAtualizado(userId);

        when(userUpdateGateway.associateUserType(userId, userTypeId)).thenReturn(userReturned);

        // ACT
        User result = uppdateUserUseCase.userAssociate(userId, userTypeId);

        // ASSERT
        assertNotNull(result);
        assertEquals(userReturned, result);
        verify(userUpdateGateway).associateUserType(userId, userTypeId);
    }
}