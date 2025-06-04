package pos.java.bora_comer.core.usercase.login.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.gateway.login.UserLoginGateway;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;

import java.util.Optional;

@Service
public class UserLoginUseCaseImpl implements UserLoginUseCase {

    private final UserLoginGateway userLoginGateway;

    public UserLoginUseCaseImpl(UserLoginGateway userLoginGateway) {
        this.userLoginGateway = userLoginGateway;
    }

    @Override
    public LoginResponseEnum execute(String login, String password) {
        Optional<User> user = userLoginGateway.findByLogin(login);

        if (user.isEmpty()) {
            return LoginResponseEnum.INVALID_LOGIN;
        }

        if (!user.get().getPassword().equals(password)) {
            return LoginResponseEnum.INVALID_PASSWORD;
        }

        return LoginResponseEnum.SUCCESS;
    }
}
