package be.kdg.integration5.gameplatformcontext.adapter.config.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTopology {

    private static final String STATISTICS_EXCHANGE = "statistics_events";
    private static final String NEW_PLAYER_QUEUE = "game_start_queue";
    private static final String NEW_GAME_QUEUE = "game_end_queue";

    @Bean
    TopicExchange statisticsEventsExchange() {
        return new TopicExchange(STATISTICS_EXCHANGE);
    }

    @Bean
    Queue newPlayerQueue() {
        return new Queue(NEW_PLAYER_QUEUE, true);
    }

    @Bean
    Binding newPlayerBinding(TopicExchange statisticsEventsExchange, Queue newPlayerQueue) {
        return BindingBuilder
                .bind(newPlayerQueue)
                .to(statisticsEventsExchange)
                .with("player.#.registered");
    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
