package school.sptech.Simbiosys.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RelatorioProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.relatorio}")
    private String queueName;

    public RelatorioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensagem(String mensagem) {
        rabbitTemplate.convertAndSend(queueName, mensagem);
        System.out.println("📩 Mensagem enviada para a fila: " + mensagem);
    }
}

