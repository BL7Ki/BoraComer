package pos.java.bora_comer.infra.gateway.user.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Component
public class UserUpdateGatewayImpl implements UserUpdateGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserUpdateGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public User update(User user) throws UserDomainException {
        // Implementação do método para atualizar o usuário
        var userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + user.getId() + " not found"));

        // Atualiza os campos necessários na entidade
        userEntity.updateName(user.getName());
        userEntity.updateEmail(user.getEmail());
        userEntity.upadatePassword(user.getPassword());
        userEntity.updateAddress(user.getAddress());
        userEntity.updateLastModifiedDate();

        // Salva a entidade atualizada
        var updatedUserEntity = userRepository.save(userEntity);

        // Retorna o domínio atualizado
        return userMapper.toDomain(updatedUserEntity);
    }
}
