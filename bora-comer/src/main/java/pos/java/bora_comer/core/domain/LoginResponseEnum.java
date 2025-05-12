package pos.java.bora_comer.core.domain;

public enum LoginResponseEnum {

    SUCCESS("Login realizado com sucesso"),
    INVALID_LOGIN("Login inválido"),
    INVALID_PASSWORD("Senha inválida");

    private final String message;

    LoginResponseEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
