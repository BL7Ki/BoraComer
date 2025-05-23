package pos.java.bora_comer.core.usercase.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.UserMapper;
import pos.java.bora_comer.core.usercase.UppdateUserUseCase;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Service
public class UppdateUserUseCaseImpl implements UppdateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UppdateUserUseCaseImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User execute(User user) throws UserDomainException {
        var userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserDomainException("User with ID " + user.getId() + " not found"));

        // Atualiza os campos necessários
        userEntity.updateName(user.getName());
        userEntity.updateEmail(user.getEmail());
        userEntity.upadatePassword(user.getPassword());
        userEntity.updateAddress(user.getAddress());
        userEntity.updateLastModifiedDate();

        // Salva as alterações
        var updatedUserEntity = userRepository.save(userEntity);

        // Retorna o domínio atualizado
        return userMapper.toDomain(updatedUserEntity);
    }
}
