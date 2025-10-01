package pos.java.bora_comer.infra.gateway.login.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.gateway.login.UserLoginGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

import java.util.Optional;

@Component
public class UserLoginGatewayImpl implements UserLoginGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserLoginGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByLogin(String username) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if (userEntity.isEmpty()) {
            return Optional.empty();
        }

        User user = userMapper.toDomain(userEntity.get(), userEntity.get().getUserTypeEntity());

        return Optional.of(user);
    }
}
