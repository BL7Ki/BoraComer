package pos.java.bora_comer.infra.gateway.userType.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.UserTypeCreateGateway;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;

@Component
public class UserTypeCreateGatewayImpl implements UserTypeCreateGateway {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public UserTypeCreateGatewayImpl(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Transactional
    @Override
    public UserType save(UserType userType) throws UserDomainException {

        UserTypeEntity userTypeEntity = userTypeMapper.toEntity(userType);

        if (userTypeRepository.existsByName(userTypeEntity.getName())) {
            throw new UserDomainException("User type already exists with name: " + userTypeEntity.getName());
        }

        UserTypeEntity savedUserTypeEntity = userTypeRepository.save(userTypeEntity);

        return userTypeMapper.toDomain(savedUserTypeEntity);
    }
}
