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
    private static final String LOBBY_EVENTS_EXCHANGE = "lobby_events";
    private static final String LOBBY_QUEUE = "lobby_queue";

    private static final String GAME_EVENTS_EXCHANGE = "game_events";
    private static final String GAME_START_QUEUE = "game_start_queue";
    private static final String GAME_END_QUEUE = "game_end_queue";
    private static final String PLAYER_MOVE_QUEUE = "player_move_queue";

    @Bean
    TopicExchange lobbyEventsExchange() {
        return new TopicExchange(LOBBY_EVENTS_EXCHANGE);
    }

    @Bean
    Queue lobbyQueue() {
        return new Queue(LOBBY_QUEUE, true);
    }

    @Bean
    Binding lobbyBinding(TopicExchange lobbyEventsExchange, Queue lobbyQueue) {
        return BindingBuilder
                .bind(lobbyQueue)
                .to(lobbyEventsExchange)
                .with("lobby.#.created");
    }

    @Bean
    TopicExchange gameEventsExchange() {
        return new TopicExchange(GAME_EVENTS_EXCHANGE);
    }

    @Bean
    Queue gameStartQueue() {
        return new Queue(GAME_START_QUEUE, true);
    }

    @Bean
    Queue gameEndQueue() {
        return new Queue(GAME_END_QUEUE, true);
    }

    @Bean
    Queue playerMoveQueue() {
        return new Queue(PLAYER_MOVE_QUEUE, true);
    }

    @Bean
    Binding gameStartBinding(TopicExchange gameEventsExchange, Queue gameStartQueue) {
        return BindingBuilder
                .bind(gameStartQueue)
                .to(gameEventsExchange)
                .with("game.#.started");
    }

    @Bean
    Binding playerMoveBinding(TopicExchange gameEventsExchange, Queue playerMoveQueue) {
        return BindingBuilder
                .bind(playerMoveQueue)
                .to(gameEventsExchange)
                .with("player.#.moved");
    }

    @Bean
    Binding gameFinishBinding(TopicExchange gameEventsExchange, Queue gameEndQueue) {
        return BindingBuilder
                .bind(gameEndQueue)
                .to(gameEventsExchange)
                .with("game.#.finished");
    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
