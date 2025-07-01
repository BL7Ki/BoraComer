package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
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
        // Arrange
        Long id = 1L;
        var user = UserTestFactory.umUserAtualizado(id);
        var userEntity = UserTestFactory.umUserEntityPadrao();

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        // Act
        var result = userUpdateGateway.update(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findById(id);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity);
    }

    @Test
    void deveLancarIllegalArgumentExceptionQuandoUsuarioNaoExistir() {
        // Arrange
        Long id = 2L;
        var user = UserTestFactory.umUserAtualizado(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> userUpdateGateway.update(user));

        assertEquals("User with ID 2 not found", exception.getMessage());
        verify(userRepository).findById(id);
        verify(userRepository, never()).save(any());
        verifyNoInteractions(userMapper);
    }

    @Test
    void deveAtualizarUsuarioComIdRandomicoUsandoFactory() throws UserDomainException {
        // Arrange
        var user = UserTestFactory.umUserComIdRandomico();
        var id = user.getId();
        var userEntity = UserTestFactory.umUserEntityComDadosDe(user);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        // Act
        var result = userUpdateGateway.update(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userRepository).findById(id);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity);
    }
}
