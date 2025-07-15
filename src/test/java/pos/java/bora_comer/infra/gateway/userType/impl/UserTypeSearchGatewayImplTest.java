package pos.java.bora_comer.infra.gateway.userType.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.factory.user.UserTypeFactory;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserTypeSearchGatewayImplTest {

    private UserTypeRepository userTypeRepository;
    private UserTypeMapper userTypeMapper;
    private UserTypeSearchGatewayImpl searchUserTypeGateway;

    @BeforeEach
    void setUp() {
        userTypeRepository = mock(UserTypeRepository.class);
        userTypeMapper = mock(UserTypeMapper.class);
        searchUserTypeGateway = new UserTypeSearchGatewayImpl(userTypeRepository, userTypeMapper);
    }

    @Test
    void deveRetornarPaginaDeUserType() {
        var pageable = PageRequest.of(0, 2);

        UserType userType = UserTypeFactory.createUserType();

        UserTypeEntity userTypeEntiy = UserTypeFactory.createUserTypeEntity();

        when(userTypeRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(userTypeEntiy), pageable, 1));
        when(userTypeMapper.toDomain(userTypeEntiy)).thenReturn(userType);

        Page<UserType> result = searchUserTypeGateway.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(userType, result.getContent().getFirst());
        verify(userTypeRepository).findAll(pageable);
        verify(userTypeMapper).toDomain(userTypeEntiy);
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHouverRegistros() {
        var pageable = PageRequest.of(0, 2);
        when(userTypeRepository.findAll(pageable)).thenReturn(Page.empty(pageable));

        Page<UserType> result = searchUserTypeGateway.findAll(pageable);

        assertTrue(result.isEmpty());
        verify(userTypeRepository).findAll(pageable);
        verifyNoInteractions(userTypeMapper);
    }

    @Test
    void deveRetornarUserTypeQuandoEncontrado() {
        UserTypeEntity entity = UserTypeFactory.createUserTypeEntity();
        UserType userType = UserTypeFactory.createUserType();

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userTypeMapper.toDomain(entity)).thenReturn(userType);

        Optional<UserType> result = searchUserTypeGateway.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
        verify(userTypeRepository).findById(1L);
        verify(userTypeMapper).toDomain(entity);
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrado() {
        when(userTypeRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UserType> result = searchUserTypeGateway.findById(2L);

        assertTrue(result.isEmpty());
        verify(userTypeRepository).findById(2L);
        verifyNoInteractions(userTypeMapper);
    }

}