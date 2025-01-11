package be.kdg.integration5.checkerscontext.adapter.out.messaging;

import be.kdg.integration5.checkerscontext.adapter.out.messaging.event.CheckersMoveMadeEvent;
import be.kdg.integration5.checkerscontext.domain.PiecePosition;
import be.kdg.integration5.checkerscontext.port.out.CheckersMoveMadeCommand;
import be.kdg.integration5.checkerscontext.port.out.NotifyCheckersMoveMadePort;
import be.kdg.integration5.common.events.PlayerMoveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CheckersMoveMadePublisher implements NotifyCheckersMoveMadePort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEndEventPublisher.class);
    private static final String EXCHANGE_NAME = "game_events";

    private final RabbitTemplate rabbitTemplate;

    public CheckersMoveMadePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyCheckersMoveMade(CheckersMoveMadeCommand checkersMoveMadeCommand) {
        UUID gameUUID = checkersMoveMadeCommand.gameId().uuid();
        final String ROUTING_KEY = String.format("player.%s.moved", UUID.randomUUID());
        LOGGER.info("Notifying RabbitMQ: {}", ROUTING_KEY);

        UUID moverId = checkersMoveMadeCommand.moverId().uuid();
        UUID nextPlayerId = checkersMoveMadeCommand.nextPlayerId().uuid();

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, new PlayerMoveEvent(
                gameUUID,
                moverId,
                nextPlayerId,
                LocalDateTime.now()
        ));
    }
}
