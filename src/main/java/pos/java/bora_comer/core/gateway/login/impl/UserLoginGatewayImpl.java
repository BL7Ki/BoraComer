package pos.java.bora_comer.core.gateway.login.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.gateway.login.UserLoginGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

@Component
public class UserLoginGatewayImpl implements UserLoginGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserLoginGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::toDomain);
    }
}
