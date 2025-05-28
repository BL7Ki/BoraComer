package pos.java.bora_comer.core.usercase.login.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

import java.util.Optional;

@Service
public class UserLoginUseCaseImpl implements UserLoginUseCase {

    private final UserRepository userRepository;

    public UserLoginUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseEnum execute(String login, String password) {
        Optional<UserEntity> userEntity = userRepository.findByLogin(login);

        if (userEntity.isEmpty()) {
            return LoginResponseEnum.INVALID_LOGIN;
        }

        UserEntity user = userEntity.get();
        if (!user.getPassword().equals(password)) {
            return LoginResponseEnum.INVALID_PASSWORD;
        }

        return LoginResponseEnum.SUCCESS;
    }
}
