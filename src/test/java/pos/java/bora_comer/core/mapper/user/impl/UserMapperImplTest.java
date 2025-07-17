package pos.java.bora_comer.core.mapper.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.mapper.user.impl.UserMapperImpl;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.factory.user.UserFactory;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserMapperImplTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void deveConverterUserRequestDTOParaDomain() {

        UserRequestDTO requestDTO = UserFactory.createUserRequestDTO();

        User user = userMapper.toDomain(requestDTO);

        assertEquals("Leo Messi", user.getName());
        assertEquals("messi10", user.getUsername());
        assertEquals("senha123", user.getPassword());
        assertEquals(UserRoleEnum.DEFAULT, user.getUserRoleEnum());
        assertNotNull(user.getAddress());
    }

    @Test
    void deveConverterDomainParaEntity() {
        User user =  UserFactory.umUserPadrao();

        UserEntity entity = userMapper.toEntity(user, 1L);

        assertEquals("Messi", entity.getName());
        assertEquals("messi@ex.com", entity.getEmail());
        assertEquals(UserRoleEntityEnum.DEFAULT, entity.getRole());
        assertNotNull(entity.getAddress());
    }

    @Test
    void deveConverterEntityParaDomain() {

        UserEntity userEntity = UserFactory.umUserEntityPadrao();
        UserTypeEntity userTypeEntity = UserTypeEntity.create(UserTypeNameEntityEnum.DONO_RESTAURANTE);


        // Definir campos privados via reflection
        ReflectionTestUtils.setField(userEntity, "id", 1L);
        ReflectionTestUtils.setField(userEntity, "lastModifiedDate", LocalDateTime.of(2024, 6, 25, 0, 0));

        User user = userMapper.toDomain(userEntity, userTypeEntity);

        assertEquals("Messi", user.getName());
        assertEquals("messi@ex.com", user.getEmail());
        assertEquals(UserRoleEnum.DEFAULT, user.getUserRoleEnum());
        assertNotNull(user.getAddress());
    }

    @Test
    void deveConverterDomainParaResponseDTO() {
        Address address = Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        User user = UserFactory.umUserPadraoCliente();

        UserResponseDTO dto = userMapper.toResponseDTO(user);

        assertEquals("Messi", dto.name());
        assertEquals("messi@ex.com", dto.email());
        assertEquals("DEFAULT", dto.userRoleEnum());
    }

    @Test
    void deveConverterUserUpdateRequestDTOParaDomain() {

        UserUpdateRequestDTO updateDTO = UserFactory.createUserUpdateRequestDTO();

        User user = userMapper.toDomain(updateDTO, 1L);

        assertEquals("Messi", user.getName());
        assertEquals("messi@ex.com", user.getEmail());
        assertEquals("NovaSenha@123", user.getPassword());
        assertEquals(1L, user.getId());
    }

    @Test
    void deveLancarExcecaoQuandoUserRoleRequestEnumForNulo() {

        UserRequestDTO requestDTO = UserFactory.createUserRoleNullRequestDTO();

        UserDomainException exception = assertThrows(UserDomainException.class, () -> {
            userMapper.toDomain(requestDTO);
        });

        assertEquals("UserRoleRequestEnumDTO não pode ser nulo", exception.getMessage());
    }
}
