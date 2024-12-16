package be.kdg.integration5.checkerscontext.adapter.config.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTopology {
    private static final String LOBBY_EVENTS_EXCHANGE = "warehouse_events";
    private static final String LOBBY_QUEUE = "lobby_queue";

    @Bean
    TopicExchange lobbyEventsExchange() {
        return new TopicExchange(LOBBY_EVENTS_EXCHANGE);
    }

    @Bean
    Queue lobbyQueue() {
        return new Queue(LOBBY_QUEUE, true);
    }

    @Bean
    Binding warehosueDumpBinding(TopicExchange lobbyEventsExchange, Queue lobbyQueue) {
        return BindingBuilder
                .bind(lobbyQueue)
                .to(lobbyEventsExchange)
                .with("lobby.#.created");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
