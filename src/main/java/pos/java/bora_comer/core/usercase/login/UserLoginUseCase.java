package pos.java.bora_comer.core.usercase.login;

import pos.java.bora_comer.core.domain.login.LoginEnum;

public interface UserLoginUseCase {

    String execute(String login, String password); // agora retorna o JWT
}
