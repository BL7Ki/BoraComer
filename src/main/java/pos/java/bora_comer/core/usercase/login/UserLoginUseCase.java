package pos.java.bora_comer.core.usercase.login;

import pos.java.bora_comer.core.domain.login.LoginEnum;

public interface UserLoginUseCase {

    LoginEnum execute(String login, String password);
}
