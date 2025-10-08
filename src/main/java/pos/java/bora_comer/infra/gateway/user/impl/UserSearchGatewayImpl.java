package pos.java.bora_comer.infra.gateway.user.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

import java.util.Optional;

@Component
public class UserSearchGatewayImpl implements UserSearchGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserSearchGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userEntity -> userMapper.toDomain(userEntity, userEntity.getUserTypeEntity()));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> userMapper.toDomain(userEntity, userEntity.getUserTypeEntity()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userEntity -> userMapper.toDomain(userEntity, userEntity.getUserTypeEntity()));
    }
}
