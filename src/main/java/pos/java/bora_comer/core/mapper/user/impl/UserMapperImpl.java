package pos.java.bora_comer.core.mapper.user.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.*;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.delivery.user.dto.UserRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserRoleRequestEnumDTO;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

@Component
public class UserMapperImpl implements UserMapper {

    // mapeamento de entrada de usuário para domínio
    @Override
    public User toDomain(UserRequestDTO userRequestDTO) {
        return User.create(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.username(),
                userRequestDTO.password(),
                Address.create(
                        userRequestDTO.addressRequestDTO().street(),
                        userRequestDTO.addressRequestDTO().neighborhood(),
                        userRequestDTO.addressRequestDTO().city(),
                        userRequestDTO.addressRequestDTO().state(),
                        userRequestDTO.addressRequestDTO().zipCode()
                ),
                toUserTypeEnumRequestConverter(userRequestDTO.userRole()),
                null, null,
                userRequestDTO.userType() != null ? toUserTypeEnumRequestConverter(userRequestDTO.userType().name()) : null
        );
    }

    // mapeamento de domínio de usuário para entidade

    @Override
    public UserEntity toEntity(User user, Long id) {
        return UserEntity.create(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                AddressEntity.create(
                        user.getAddress().getStreet(),
                        user.getAddress().getNeighborhood(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getZipCode()
                ),
                user.getUserRoleEnum() != null ? toUserTypeEnumEntityConverter(user.getUserRoleEnum()) : null,
                id
        );
    }

    @Override
    public UserEntity toEntity(User user) {
        return UserEntity.create(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                AddressEntity.create(
                        user.getAddress().getStreet(),
                        user.getAddress().getNeighborhood(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getZipCode()
                ),
                user.getUserRoleEnum() != null ? toUserTypeEnumEntityConverter(user.getUserRoleEnum()) : null,
                null
        );
    }

    // mapeamento de entidade de usuário para domínio
    @Override
    public User toDomain(UserEntity userEntity, UserTypeEntity userTypeEntity) {
        return User.create(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                Address.create(
                        userEntity.getAddress().getStreet(),
                        userEntity.getAddress().getNeighborhood(),
                        userEntity.getAddress().getCity(),
                        userEntity.getAddress().getState(),
                        userEntity.getAddress().getZipCode()
                ),
                toUserTypeEnumEntityConverter(userEntity.getRole()),
                userEntity.getCreatedDate().toString(),
                userEntity.getLastModifiedDate() != null ? userEntity.getLastModifiedDate().toString() : null,
                userTypeEntity != null ? UserTypeNameEnum.valueOf(userTypeEntity.getName().name()) : null
        );
    }

    // mapeamento de dominio de usuário para DTO de resposta

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                new AddressResponseDTO(
                        user.getAddress().getStreet(),
                        user.getAddress().getNeighborhood(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getZipCode()
                ),
                user.getUserRoleEnum() != null ? user.getUserRoleEnum().name() : null,
                user.getCreatedDate(),
                user.getLastModifiedDate(),
                user.getUserTypeNameEnum() != null ? user.getUserTypeNameEnum().name() : null
        );
    }

    // mapeamento de DTO de atualização de usuário para domínio

    @Override
    public User toDomain(UserUpdateRequestDTO userUpdateRequestDTO, Long id) {
        return User.create(
                id,
                userUpdateRequestDTO.name(),
                userUpdateRequestDTO.email(),
                null,
                userUpdateRequestDTO.password(),
                Address.create(
                        userUpdateRequestDTO.address().street(),
                        userUpdateRequestDTO.address().neighborhood(),
                        userUpdateRequestDTO.address().city(),
                        userUpdateRequestDTO.address().state(),
                        userUpdateRequestDTO.address().zipCode()
                ),
                null,
                null,
                null,
                userUpdateRequestDTO.userType() != null ? toUserTypeEnumRequestConverter(userUpdateRequestDTO.userType()) : null
        );
    }

    private UserRoleEnum toUserTypeEnumRequestConverter(UserRoleRequestEnumDTO requestEnum) {
        if (requestEnum == null) {
            throw new UserDomainException("UserRoleRequestEnumDTO não pode ser nulo");
        }
        return UserRoleEnum.valueOf(requestEnum.name());
    }

    private UserRoleEnum toUserTypeEnumEntityConverter(UserRoleEntityEnum userRoleEntityEnum) {
        if (userRoleEntityEnum == null) {
            throw new UserDomainException("UserRoleEnum não pode ser nulo");
        }
        return UserRoleEnum.valueOf(userRoleEntityEnum.name());
    }

    private UserRoleEntityEnum toUserTypeEnumEntityConverter(UserRoleEnum userRoleEnum) {
        if (userRoleEnum == null) {
            throw new UserDomainException("UserRoleEnum não pode ser nulo");
        }
        return UserRoleEntityEnum.valueOf(userRoleEnum.name());
    }

    private UserTypeNameEnum toUserTypeEnumRequestConverter(UserTypeNameRequestEnum requestEnum) {

        return UserTypeNameEnum.valueOf(requestEnum.name());
    }
}
