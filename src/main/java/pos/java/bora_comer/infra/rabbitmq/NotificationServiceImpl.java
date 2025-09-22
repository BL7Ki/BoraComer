package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.port.NotificationService;


/*
4. Comunicação Assíncrona com RabbitMQ ou Kafka
● RabbitMQ ou Kafka: utilizar uma dessas ferramentas para gerenciar a comunicação assíncrona entre os serviços.
○ O Serviço de agendamento deve enviar uma mensagem ao serviço de notificações quando uma consulta for criada ou editada.
○ O serviço de notificações processa essa mensagem e envia um lembrete ao paciente.
 */

@Service
public class NotificationServiceImpl implements NotificationService {

    // ajustar nome do metodo para ingles
    public boolean enviarLembretePaciente(String mensagem) {
        try {
            System.out.println("Atenção: " + mensagem);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao enviar lembrete: " + e.getMessage());
            return false;
        }
    }

    // ajustar nome do metodo para ingles
    public boolean enviarLembretePedidoCriado(String reminderMessage) {
        try {
            System.out.println("Lembrete: " + reminderMessage);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao enviar lembrete: " + e.getMessage());
            return false;
        }
    }
}
