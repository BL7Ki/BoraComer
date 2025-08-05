package pos.java.bora_comer.core.mapper.userType.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

@Component
public class UserTypeMapperImpl implements UserTypeMapper {

    @Override
    public UserType toDomain(UserTypeNameRequestEnum name) {
        UserTypeNameEnum userTypeNameEnum = UserTypeNameEnum.valueOf(name.name());
        return UserType.create(null, userTypeNameEnum);
    }

    @Override
    public UserType toDomain(UserTypeEntity userTypeEntity) {
        return UserType.create(
            userTypeEntity.getId(),
            toDomainConverter(userTypeEntity.getName())
        );
    }

    @Override
    public UserTypeEntity toEntity(UserType userType) {
        return UserTypeEntity.create(
           toEntityConverter(userType.getNome())
        );
    }

    @Override
    public UserTypeResponseDTO toResponse(UserType userType) {
        return new UserTypeResponseDTO(
            userType.getId(),
            userType.getNome().name()
        );
    }

    private UserTypeNameEntityEnum toEntityConverter(UserTypeNameEnum userTypeNameEnum) {
        return UserTypeNameEntityEnum.valueOf(userTypeNameEnum.name());
    }

    private UserTypeNameEnum toDomainConverter(UserTypeNameEntityEnum userTypeNameEntityEnum) {
        return UserTypeNameEnum.valueOf(userTypeNameEntityEnum.name());
    }
}
