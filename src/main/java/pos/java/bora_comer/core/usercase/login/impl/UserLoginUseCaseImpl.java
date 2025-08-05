package pos.java.bora_comer.core.usercase.login.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.login.LoginEnum;
import pos.java.bora_comer.core.domain.user.User;
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
    public LoginEnum execute(String login, String password) {
        Optional<User> user = userLoginGateway.findByLogin(login);

        if (user.isEmpty()) {
            return LoginEnum.INVALID_LOGIN;
        }

        if (!user.get().getPassword().equals(password)) {
            return LoginEnum.INVALID_PASSWORD;
        }

        return LoginEnum.SUCCESS;
    }
}
