package pos.java.bora_comer.core.mapper.user;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

public interface UserMapper {

    User toDomain(UserRequestDTO userRequestDTO);

    UserEntity toEntity(User user, Long id);

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity, UserTypeEntity userTypeEntity);

    UserResponseDTO toResponseDTO(User user);

    User toDomain(UserUpdateRequestDTO userUpdateRequestDTO, Long id);
}
