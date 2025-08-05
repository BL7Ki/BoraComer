package pos.java.bora_comer.core.mapper.userType.impl;

import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.util.factory.UserTypeFactory;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTypeMapperImplTest {

    private final UserTypeMapperImpl mapper = new UserTypeMapperImpl();

    @Test
    void deveConverterUserTypeNameRequestEnumParaDomain() {
        UserTypeNameRequestEnum requestEnum = UserTypeNameRequestEnum.CLIENTE;
        UserType userType = mapper.toDomain(requestEnum);

        assertNotNull(userType);
        assertNull(userType.getId());
        assertEquals(UserTypeNameEnum.CLIENTE, userType.getNome());
    }

    @Test
    void deveConverterUserTypeEntityParaDomain() {
        UserTypeEntity entity = UserTypeFactory.createUserTypeEntity();
        UserType userType = mapper.toDomain(entity);

        assertNotNull(userType);
        assertEquals(entity.getId(), userType.getId());
        assertEquals(UserTypeNameEnum.DONO_RESTAURANTE, userType.getNome());
    }

    @Test
    void deveConverterDomainParaUserTypeEntity() {
        UserType userType = UserTypeFactory.createUserType();
        UserTypeEntity entity = mapper.toEntity(userType);

        assertNotNull(entity);
        assertEquals(UserTypeNameEntityEnum.DONO_RESTAURANTE, entity.getName());
    }

    @Test
    void deveConverterDomainParaResponseDTO() {
        UserType userType = UserTypeFactory.createUserType();
        UserTypeResponseDTO dto = mapper.toResponse(userType);

        assertNotNull(dto);
        assertEquals(userType.getId(), dto.id());
        assertEquals(userType.getNome().name(), dto.userType());
    }
}