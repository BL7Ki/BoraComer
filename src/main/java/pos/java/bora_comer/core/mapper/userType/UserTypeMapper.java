package pos.java.bora_comer.core.mapper.userType;

import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

public interface UserTypeMapper {

    UserType toDomain(UserTypeNameRequestEnum name);

    UserType toDomain(UserTypeEntity userTypeEntity);

    UserTypeEntity toEntity(UserType userType);

    UserTypeResponseDTO toResponse(UserType userType);
}
