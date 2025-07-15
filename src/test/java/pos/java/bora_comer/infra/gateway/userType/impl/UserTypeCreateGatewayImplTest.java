package pos.java.bora_comer.infra.gateway.userType.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.factory.user.UserTypeFactory;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserTypeCreateGatewayImplTest {

    private UserTypeRepository userTypeRepository;
    private UserTypeMapper userTypeMapper;
    private UserTypeCreateGatewayImpl userTypeCreateGateway;

    @BeforeEach
    void setUp() {
        userTypeRepository = mock(UserTypeRepository.class);
        userTypeMapper = mock(UserTypeMapper.class);
        userTypeCreateGateway = new UserTypeCreateGatewayImpl(userTypeRepository, userTypeMapper);
    }

    @Test
    void deveSalvarUserTypeComSucesso() {

        UserType userType = UserTypeFactory.createUserType();

        UserTypeEntity userTypeEntity = mock(UserTypeEntity.class);
        UserTypeEntity savedEntity = mock(UserTypeEntity.class);
        UserType userTypeRetornado = mock(UserType.class);

        when(userTypeMapper.toEntity(userType)).thenReturn(userTypeEntity);
        when(userTypeEntity.getName()).thenReturn(UserTypeNameEntityEnum.DONO_RESTAURANTE);
        when(userTypeRepository.existsByName(UserTypeNameEntityEnum.DONO_RESTAURANTE)).thenReturn(false);
        when(userTypeRepository.save(userTypeEntity)).thenReturn(savedEntity);
        when(userTypeMapper.toDomain(savedEntity)).thenReturn(userTypeRetornado);

        UserType result = userTypeCreateGateway.save(userType);

        assertEquals(userTypeRetornado, result);
        verify(userTypeRepository).save(userTypeEntity);
    }

    @Test
    void deveLancarExcecaoQuandoNomeJaExiste() {
        UserType userType = UserTypeFactory.createUserType();

        UserTypeEntity userTypeEntity = UserTypeFactory.createUserTypeEntity();

        when(userTypeMapper.toEntity(userType)).thenReturn(userTypeEntity);

        when(userTypeRepository.existsByName(UserTypeNameEntityEnum.DONO_RESTAURANTE)).thenReturn(true);

        UserDomainException exception = assertThrows(
                UserDomainException.class,
                () -> userTypeCreateGateway.save(userType)
        );

        assertTrue(exception.getMessage().contains("User type already exists with name: DONO_RESTAURANTE"));
        verify(userTypeRepository, Mockito.times(0)).save(any());
    }
}