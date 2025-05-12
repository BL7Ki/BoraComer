package pos.java.bora_comer.core.mapper;

import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

public interface UserMapper {

    User toDomain(UserRequestDTO userRequestDTO);

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);

    UserResponseDTO toResponseDTO(User user);

    User toDomain(UserUpdateRequestDTO userUpdateRequestDTO, Long id);
}
