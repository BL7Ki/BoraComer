package pos.java.bora_comer.core.usercase.login;

public interface UserLoginUseCase {
    String execute(String login, String password); // agora retorna o JWT
}
