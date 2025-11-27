package school.sptech.Simbiosys.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

@Service
public class RelatorioProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    @Value("${broker.routing.key}")
    private String routingKey;

    public RelatorioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarRelatorioCriado(RelatorioEntity relatorio) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, relatorio);
        System.out.println("📤 Evento publicado: " + relatorio);
    }
}


