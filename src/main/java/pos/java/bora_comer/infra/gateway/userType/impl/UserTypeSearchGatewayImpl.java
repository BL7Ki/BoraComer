package pos.java.bora_comer.infra.gateway.userType.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.gateway.userType.UserTypeSearchGateway;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

import java.util.Optional;

@Component
public class UserTypeSearchGatewayImpl implements UserTypeSearchGateway {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public UserTypeSearchGatewayImpl(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserType> findAll(Pageable pageable) {

        return userTypeRepository.findAll(pageable)
                .map(userTypeMapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserType> findById(Long id) {

        Optional<UserTypeEntity> userTypeEntity = userTypeRepository.findById(id);

        if (userTypeEntity.isEmpty()) {
            return Optional.empty();
        }

        UserType userType = userTypeMapper.toDomain(userTypeEntity.get());

        return Optional.of(userType);
    }
}
