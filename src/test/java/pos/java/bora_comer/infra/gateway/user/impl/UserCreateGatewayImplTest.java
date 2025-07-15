package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.factory.user.UserFactory;
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
        when(userTypeRepository.findByName(any()))
                .thenReturn(Optional.of(UserTypeEntity.create(UserTypeNameEntityEnum.DONO_RESTAURANTE)));

        boolean exists = userCreateGateway.existsByUsername("messi");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("messi");
    }

    @Test
    void deveSalvarUsuarioComTipoComSucesso() {
        User user = UserFactory.umUserPadrao();
        UserEntity userEntity = UserFactory.umUserEntityPadrao();
        UserTypeEntity userTypeEntity = UserTypeEntity.create(1L, UserTypeNameEntityEnum.DONO_RESTAURANTE);

        when(userTypeRepository.findByName(any())).thenReturn(Optional.of(userTypeEntity));
        when(userMapper.toEntity(user, userTypeEntity.getId())).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity, userTypeEntity)).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        verify(userTypeRepository).findByName(any());
        verify(userMapper).toEntity(user, userTypeEntity.getId());
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity, userTypeEntity);
    }

    @Test
    void deveSalvarUsuarioSemTipoComSucesso() {
        User user = UserFactory.umUserTypeNull();

        UserEntity userEntity = UserFactory.umUserEntityPadrao();

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity, null)).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        verify(userMapper).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDomain(userEntity, null);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioInvalido() {
        User user = UserFactory.umUserPadrao();

        when(userTypeRepository.findByName(any())).thenReturn(Optional.empty());

        UserDomainException ex = assertThrows(UserDomainException.class, () -> userCreateGateway.save(user));
        assertEquals("Tipo de usuário inválido", ex.getMessage());
        verify(userTypeRepository).findByName(any());
        verify(userRepository, never()).save(any());
    }
}
