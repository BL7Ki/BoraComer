package pos.java.bora_comer.infra.gateway.userType.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.gateway.userType.SearchUserTypeGateway;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;

@Component
public class SearchUserTypeGatewayImpl implements SearchUserTypeGateway {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    public SearchUserTypeGatewayImpl(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserType> findAll(Pageable pageable) {

        return userTypeRepository.findAll(pageable)
                .map(userTypeMapper::toDomain);
    }
}
