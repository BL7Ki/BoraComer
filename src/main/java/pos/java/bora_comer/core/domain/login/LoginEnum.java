package pos.java.bora_comer.core.domain.login;

public enum LoginEnum {

    SUCCESS("Login realizado com sucesso"),
    INVALID_LOGIN("Login inválido"),
    INVALID_PASSWORD("Senha inválida"),
    PASSWORD_CHANGED_SUCCESSFULLY("Senha alterada com sucesso.");

    private final String message;

    LoginEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
