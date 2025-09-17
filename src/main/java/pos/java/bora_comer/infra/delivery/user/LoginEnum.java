package pos.java.bora_comer.infra.delivery.user;

public enum LoginEnum {
    PASSWORD_CHANGED_SUCCESSFULLY("Senha alterada com sucesso");

    private final String message;
    LoginEnum(String message) { this.message = message; }
    public String getMessage() { return message; }
}
