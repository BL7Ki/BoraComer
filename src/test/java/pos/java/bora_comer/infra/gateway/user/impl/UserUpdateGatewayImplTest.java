package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.util.AddressTestFactory;
import pos.java.bora_comer.util.UserTestFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUpdateGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserUpdateGatewayImpl userUpdateGateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userUpdateGateway = new UserUpdateGatewayImpl(userRepository, userMapper);
    }

    @Test
    void deveAtualizarUsuarioQuandoExistir() throws UserDomainException {
        Long id = 1L;

        var user = User.create(id, "Messi Atualizado", "messi_novo@ex.com", "messi", "NovaSenha@123",
                AddressTestFactory.umEnderecoAtualizado(), UserRoleEnum.CLIENTE, "2024-06-25");

        var userEntity = UserEntity.create("Messi", "messi@ex.com", "messi", "Messi@123",
                AddressTestFactory.umEnderecoEntityPadrao(), UserRoleEntityEnum.CLIENTE);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        var result = userUpdateGateway.update(user);

        assertNotNull(result);
        assertEquals("Messi Atualizado", result.getName());
        assertEquals("messi_novo@ex.com", result.getEmail());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDomain(userEntity);
    }

    @Test
    void deveLancarIllegalArgumentExceptionQuandoUsuarioNaoExistir() {
        Long id = 2L;

        var user = User.create(id, "Messi Atualizado", "messi_novo@ex.com", "messi", "NovaSenha@123",
                AddressTestFactory.umEnderecoAtualizado(), UserRoleEnum.CLIENTE, "2024-06-25");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> userUpdateGateway.update(user));

        assertEquals("User with ID 2 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any());
        verifyNoInteractions(userMapper);
    }

    @Test
    void deveAtualizarUsuarioComIdRandomicoUsandoFactory() throws UserDomainException {
        var user = UserTestFactory.umUserComIdRandomico();
        var id = user.getId();

        var userEntity = UserEntity.create(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                AddressTestFactory.umEnderecoEntityPadrao(),
                UserRoleEntityEnum.CLIENTE
        );

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        var result = userUpdateGateway.update(user);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userRepository).findById(id);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity);
    }
}
