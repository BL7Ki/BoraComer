package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.util.factory.UserTestFactory;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCreateGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserCreateGatewayImpl userCreateGateway;
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userTypeRepository = mock(UserTypeRepository.class);
        userCreateGateway = new UserCreateGatewayImpl(userRepository, userMapper, userTypeRepository);
    }

    @Test
    void deveVerificarSeUsernameExiste() {
        when(userRepository.existsByUsername("messi")).thenReturn(true);

        boolean exists = userCreateGateway.existsByUsername("messi");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("messi");
    }

    @Test
    void deveSalvarUsuarioComTipoComSucesso() {
        User user = UserTestFactory.umUserPadrao();
        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();

        UserTypeEntity userTypeEntity = UserTypeEntity.create(1L, UserTypeNameEntityEnum.DONO_RESTAURANTE);
        when(userTypeRepository.findByName(any(String.class))).thenReturn(Optional.of(userTypeEntity));

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity, userTypeEntity)).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        verify(userTypeRepository).findByName(any(String.class));
        verify(userMapper).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity, userTypeEntity);
    }

    @Test
    void deveSalvarUsuarioSemTipoComSucesso() {
        User user = UserTestFactory.umUserTypeNull();

        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity, null)).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        verify(userMapper).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity, null);
        verify(userTypeRepository, never()).findByName(any(String.class));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioInvalido() {
        User user = UserTestFactory.umUserPadrao();

        // Mock para simular que o tipo de usuário não foi encontrado
        when(userTypeRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        UserDomainException ex = assertThrows(UserDomainException.class, () -> userCreateGateway.save(user));
        assertEquals("Tipo de usuário inválido", ex.getMessage());

        verify(userTypeRepository).findByName(any(String.class));
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toEntity(any(User.class));
    }
}