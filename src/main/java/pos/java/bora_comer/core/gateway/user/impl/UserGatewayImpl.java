package pos.java.bora_comer.core.gateway.user.impl;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.gateway.user.UserGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public  UserGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean existsByUsername(String username) {
        // Implementação do método para verificar se o usuário existe pelo nome de usuário
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        // Implementação do método para salvar o usuário
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }
}
