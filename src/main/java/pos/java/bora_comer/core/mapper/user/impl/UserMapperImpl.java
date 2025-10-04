package pos.java.bora_comer.core.mapper.user.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.delivery.user.dto.*;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

    private final UserTypeRepository userTypeRepository;

    public UserMapperImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public User toDomain(UserRequestDTO userRequestDTO) {
        return User.createNew(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.username(),
                userRequestDTO.password(),
                toUserRoleEnumConverter(userRequestDTO.userRole()),
                userRequestDTO.userType() != null ? toUserTypeNameEnumRequestConverter(userRequestDTO.userType()) : null
        );
    }

    private Long findUserTypeId(UserTypeNameEnum userTypeName) {
        if (userTypeName == null) {
            throw new UserDomainException("UserTypeNameEnum não pode ser nulo para conversão em Entidade.");
        }

        Optional<UserTypeEntity> entityOpt = userTypeRepository.findByName(userTypeName.name());

        if (entityOpt.isEmpty()) {
            throw new UserDomainException("Tipo de Usuário não encontrado na base de dados: " + userTypeName.name());
        }

        return entityOpt.get().getId();
    }

    @Override
    public UserEntity toEntity(User user, Long id) {
        Long userTypeId = findUserTypeId(user.getUserTypeNameEnum());

        return UserEntity.create(
                id,
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
                toUserRoleEntityConverter(user.getUserRoleEnum()),
                userTypeId
        );
    }

    @Override
    public UserEntity toEntity(User user) {
        Long userTypeId = findUserTypeId(user.getUserTypeNameEnum());

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
                toUserRoleEntityConverter(user.getUserRoleEnum()),
                userTypeId
        );
    }

    @Override
    public User toDomain(UserEntity userEntity, UserTypeEntity userTypeEntity) {
        return User.reconstruct(
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
                toUserRoleEnumConverter(userEntity.getRole()),
                userEntity.getCreatedDate(),
                userEntity.getLastModifiedDate(),
                userTypeEntity != null ? UserTypeNameEnum.valueOf(userTypeEntity.getName().name()) : null
        );
    }

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
                user.getCreatedDate().toString(),
                user.getLastModifiedDate() != null ? user.getLastModifiedDate().toString() : null,
                user.getUserTypeNameEnum() != null ? user.getUserTypeNameEnum().name() : null
        );
    }

    @Override
    public User toDomain(UserUpdateRequestDTO userUpdateRequestDTO, Long id) {
        throw new UnsupportedOperationException("O Mapper não deve criar Domínio para Update. Use o UserService para buscar/aplicar/salvar.");
    }

    private UserRoleEnum toUserRoleEnumConverter(UserRoleRequestEnumDTO requestEnum) {
        if (requestEnum == null) {
            throw new UserDomainException("UserRoleRequestEnumDTO não pode ser nulo");
        }
        return UserRoleEnum.valueOf(requestEnum.name());
    }

    private UserRoleEnum toUserRoleEnumConverter(UserRoleEntityEnum userRoleEntityEnum) {
        if (userRoleEntityEnum == null) {
            throw new UserDomainException("UserRoleEntityEnum não pode ser nulo");
        }
        return UserRoleEnum.valueOf(userRoleEntityEnum.name());
    }

    private UserRoleEntityEnum toUserRoleEntityConverter(UserRoleEnum userRoleEnum) {
        if (userRoleEnum == null) {
            throw new UserDomainException("UserRoleEnum não pode ser nulo");
        }
        return UserRoleEntityEnum.valueOf(userRoleEnum.name());
    }

    private UserTypeNameEnum toUserTypeNameEnumRequestConverter(UserTypeNameRequestEnum requestEnum) {
        if (requestEnum == null) {
            throw new UserDomainException("UserTypeNameRequestEnum não pode ser nulo");
        }
        return UserTypeNameEnum.valueOf(requestEnum.name());
    }
}