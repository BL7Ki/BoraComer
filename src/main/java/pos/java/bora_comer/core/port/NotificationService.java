package pos.java.bora_comer.core.port;

public interface NotificationService {

    boolean enviarLembretePaciente(String mensagem);

    boolean enviarLembretePedidoCriado(String reminderMessage);
}
