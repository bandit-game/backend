package be.kdg.integration5.checkerscontext.adapter.out.move;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.out.NotifyMoveMadePort;
import be.kdg.integration5.common.events.MoveMadeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MoveMadeEventPublisher implements NotifyMoveMadePort {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveMadeEventPublisher.class);
    private static final String EXCHANGE_NAME = "games_events";

    private final RabbitTemplate rabbitTemplate;

    public MoveMadeEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyMoveMade(GameId gameId, PlayerId playerId, Move move) {
        final String ROUTING_KEY = "game.%s.moves.new".formatted(gameId.uuid());
        LOGGER.info("Notifying RabbitMQ: {}", ROUTING_KEY);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, new MoveMadeEvent(
                gameId.uuid(),
                playerId.uuid(),
                move.getInitialPosition().x(),
                move.getInitialPosition().y(),
                move.getFuturePosition().x(),
                move.getFuturePosition().y()
        ));

    }
}
