package pos.java.bora_comer.core.mapper.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {

    private UserMapper userMapper;

    @Mock
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl(userTypeRepository);
    }

    @Test
    void deveConverterUserRequestDTOParaDomain() {
        UserRequestDTO requestDTO = UserTestFactory.createUserRequestDTO();

        User user = userMapper.toDomain(requestDTO);

        assertEquals("Leo Messi", user.getName());
        assertEquals("messi10", user.getUsername());
        assertEquals("senha123", user.getPassword());
        assertEquals(UserRoleEnum.DEFAULT, user.getUserRoleEnum());
        assertEquals(UserTypeNameEnum.CLIENTE, user.getUserTypeNameEnum());
        assertNotNull(user.getAddress());
        assertNotNull(user.getCreatedDate());
    }

    @Test
    void deveConverterDomainParaEntity() {
        UserTypeEntity mockTypeEntity = new UserTypeEntity();
        ReflectionTestUtils.setField(mockTypeEntity, "id", 100L);

        when(userTypeRepository.findByName(anyString()))
                .thenReturn(Optional.of(mockTypeEntity));

        User user = UserTestFactory.umUserPadrao();

        UserEntity entity = userMapper.toEntity(user, 1L);

        assertEquals("Messi", entity.getName());
        assertEquals("messi@ex.com", entity.getEmail());
        assertEquals(UserRoleEntityEnum.DEFAULT, entity.getRole());
        assertNotNull(entity.getAddress());
        assertEquals(100L, entity.getUserTypeEntity().getId());
    }

    @Test
    void deveConverterEntityParaDomain() {
        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();

        UserTypeEntity userTypeEntity = new UserTypeEntity();
        ReflectionTestUtils.setField(userTypeEntity, "id", 100L);
        ReflectionTestUtils.setField(userTypeEntity, "name", UserTypeNameEntityEnum.DONO_RESTAURANTE);

        ReflectionTestUtils.setField(userEntity, "id", 1L);
        ReflectionTestUtils.setField(userEntity, "createdDate", LocalDateTime.of(2023, 1, 1, 10, 0));
        ReflectionTestUtils.setField(userEntity, "lastModifiedDate", LocalDateTime.of(2024, 6, 25, 0, 0));

        User user = userMapper.toDomain(userEntity, userTypeEntity);

        assertEquals("Messi", user.getName());
        assertEquals("messi@ex.com", user.getEmail());
        assertEquals(UserRoleEnum.DEFAULT, user.getUserRoleEnum());
        assertEquals(UserTypeNameEnum.DONO_RESTAURANTE, user.getUserTypeNameEnum());
        assertNotNull(user.getAddress());
        assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), user.getCreatedDate());
    }

    @Test
    void deveConverterDomainParaResponseDTO() {
        Address address = Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        User user = UserTestFactory.umUserPadraoCliente();
        ReflectionTestUtils.setField(user, "createdDate", LocalDateTime.of(2023, 1, 1, 10, 0));
        ReflectionTestUtils.setField(user, "lastModifiedDate", LocalDateTime.of(2023, 1, 1, 10, 0));
        ReflectionTestUtils.setField(user, "userTypeNameEnum", UserTypeNameEnum.CLIENTE);

        UserResponseDTO dto = userMapper.toResponseDTO(user);

        assertEquals("Messi", dto.name());
        assertEquals("messi@ex.com", dto.email());
        assertEquals("DEFAULT", dto.userRoleEnum());
        assertEquals("CLIENTE", dto.userType()); // CORRIGIDO: Deve ser dto.userType()
        assertNotNull(dto.createdDate());
    }

    @Test
    void deveLancarExcecaoNoMapeamentoDeUpdate() {
        UserUpdateRequestDTO updateDTO = UserTestFactory.createUserUpdateRequestDTO();

        assertThrows(UnsupportedOperationException.class, () -> userMapper.toDomain(updateDTO, 1L));
    }

    @Test
    void deveLancarExcecaoQuandoUserRoleRequestEnumForNulo() {
        UserRequestDTO requestDTO = UserTestFactory.createUserRoleNullRequestDTO();

        UserDomainException exception = assertThrows(UserDomainException.class, () -> userMapper.toDomain(requestDTO));
        assertEquals("UserRoleRequestEnumDTO não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUserTypeNaoForEncontradoNaBuscaParaEntity() {
        when(userTypeRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        User user = UserTestFactory.umUserPadrao();

        UserDomainException exception = assertThrows(UserDomainException.class, () -> userMapper.toEntity(user));
        assertEquals("Tipo de Usuário não encontrado na base de dados: CLIENTE", exception.getMessage());
    }
}