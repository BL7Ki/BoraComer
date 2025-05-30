package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserUseCaseImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    /**
     * Método para criar um novo usuário.
     *
     * @param user Objeto que representa o usuário a ser criado.
     * @return UserDomain O usuário criado.
     * @throws UserDomainException Se ocorrer algum erro durante a criação do usuário.
     */

    @Override
    public User execute(User user) throws UserDomainException {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserDomainException("O userName já está em uso.");
        }

        var userEntity = userMapper.toEntity(user);

        var userEntitySave = userRepository.save(userEntity);

        return userMapper.toDomain(userEntitySave);
    }
}
