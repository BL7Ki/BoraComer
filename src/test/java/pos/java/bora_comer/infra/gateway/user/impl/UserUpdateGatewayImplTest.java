package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUpdateGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserUpdateGatewayImpl userUpdateGateway;
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userTypeRepository = mock(UserTypeRepository.class);
        userUpdateGateway = new UserUpdateGatewayImpl(userRepository, userMapper, userTypeRepository);
    }

    @Test
    void deveAtualizarUsuarioQuandoExistir() throws UserDomainException {
        // Arrange
        Long id = 1L;
        var user = UserTestFactory.umUserAtualizado(id);
        var originalEntity = UserTestFactory.umUserEntityPadrao(); // A Entidade que o DB retorna
        var updatedEntity = UserTestFactory.umUserEntityPadrao();   // A Entidade que será salva

        when(userRepository.findById(id)).thenReturn(Optional.of(originalEntity));

        // 💡 Mock para a conversão de Domínio de volta para Entidade (aplicando as mudanças)
        when(userMapper.toEntity(user, id)).thenReturn(updatedEntity);

        when(userRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(userMapper.toDomain(updatedEntity, updatedEntity.getUserTypeEntity())).thenReturn(user);

        // Atualmente não usado no fluxo de update do Gateway, mas mantido para consistência
        when(userTypeRepository.findByName(anyString()))
                .thenReturn(Optional.of(UserTypeEntity.create(1L, UserTypeNameEntityEnum.DONO_RESTAURANTE)));

        // Act
        var result = userUpdateGateway.update(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findById(id);

        // 💡 Verifica que o Mapper foi chamado para converter o objeto de Domínio atualizado
        verify(userMapper).toEntity(user, id);

        // 💡 Verifica que a Entidade atualizada foi salva
        verify(userRepository).save(updatedEntity);
        verify(userMapper).toDomain(updatedEntity, updatedEntity.getUserTypeEntity());
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
        verify(userMapper, never()).toEntity(any(), any());
    }

    @Test
    void deveAtualizarUsuarioComIdRandomicoUsandoFactory() throws UserDomainException {
        // Arrange
        var user = UserTestFactory.umUserComIdRandomico();
        var id = user.getId();
        var originalEntity = UserTestFactory.umUserEntityComDadosDe(user); // Original
        var updatedEntity = UserTestFactory.umUserEntityComDadosDe(user); // Versão a ser salva

        when(userRepository.findById(id)).thenReturn(Optional.of(originalEntity));

        // 💡 Mock para a conversão de Domínio de volta para Entidade
        when(userMapper.toEntity(user, id)).thenReturn(updatedEntity);

        when(userRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(userMapper.toDomain(updatedEntity, updatedEntity.getUserTypeEntity())).thenReturn(user);

        when(userTypeRepository.findByName(anyString()))
                .thenReturn(Optional.of(UserTypeEntity.create(1L, UserTypeNameEntityEnum.DONO_RESTAURANTE)));

        // Act
        var result = userUpdateGateway.update(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userRepository).findById(id);

        // 💡 Verifica que o Mapper foi chamado para converter o objeto de Domínio atualizado
        verify(userMapper).toEntity(user, id);

        // 💡 Verifica que a Entidade atualizada foi salva
        verify(userRepository).save(updatedEntity);
        verify(userMapper).toDomain(updatedEntity, updatedEntity.getUserTypeEntity());
    }

    @Test
    void deveAssociarTipoUsuarioComSucesso() throws UserDomainException {
        // Arrange
        Long userId = 10L;
        Long userTypeId = 20L;
        var userEntity = spy(UserTestFactory.umUserEntityPadrao());
        var userTypeEntity = UserTypeEntity.create(userTypeId, UserTypeNameEntityEnum.DONO_RESTAURANTE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(userTypeEntity));

        // Act
        userUpdateGateway.associateUserType(userId, userTypeId);

        // Assert
        verify(userEntity).setUserTypeEntity(userTypeEntity);
        verify(userRepository).save(userEntity);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoNaAssociacao() {
        // Arrange
        Long userId = 11L;
        Long userTypeId = 21L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        var ex = assertThrows(UserDomainException.class, () -> userUpdateGateway.associateUserType(userId, userTypeId));
        assertEquals("Usuário não encontrado.", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontradoNaAssociacao() {
        // Arrange
        Long userId = 12L;
        Long userTypeId = 22L;
        var userEntity = UserTestFactory.umUserEntityPadrao();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

        // Act & Assert
        var ex = assertThrows(UserDomainException.class, () -> userUpdateGateway.associateUserType(userId, userTypeId));
        assertEquals("Tipo de usuário não encontrado.", ex.getMessage());
        verify(userRepository, never()).save(any());
    }
}