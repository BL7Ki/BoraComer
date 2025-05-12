package pos.java.bora_comer.core;

import pos.java.bora_comer.core.domain.LoginResponseEnum;

public interface UserLoginUseCase {

    LoginResponseEnum execute(String login, String password);
}
