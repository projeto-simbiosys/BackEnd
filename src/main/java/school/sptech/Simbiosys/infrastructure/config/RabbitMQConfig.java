package school.sptech.Simbiosys.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.name}")
    private String queueName;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    @Value("${broker.routing.key}")
    private String routingKey;

    @Bean
    public Queue relatorioQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange relatorioExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue relatorioQueue, DirectExchange relatorioExchange) {
        return BindingBuilder.bind(relatorioQueue).to(relatorioExchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

