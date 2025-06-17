package pos.java.bora_comer.infra.gateway.user.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.gateway.user.UserCreateGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

@Component
public class UserCreateGatewayImpl implements UserCreateGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserCreateGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean existsByUsername(String username) {
        // Implementação do método para verificar se o usuário existe pelo nome de usuário
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public User save(User user) {
        // Implementação do método para salvar o usuário
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }
}
