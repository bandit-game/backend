package be.kdg.integration5.checkerscontext.adapter.out.messaging;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.port.out.NotifyGameEndPort;
import be.kdg.integration5.common.events.FinishGameSessionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class GameEndEventPublisher implements NotifyGameEndPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEndEventPublisher.class);
    private static final String EXCHANGE_NAME = "game_events";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GameEndEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyGameEnd(Game game) {
        final String ROUTING_KEY = "warehouse.%s.material.dumped".formatted();
        LOGGER.info("Notifying RabbitMQ: {}", ROUTING_KEY);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, new FinishGameSessionEvent(
                game.getPlayedMatchId().uuid(),
                game.getWinner(),
                LocalDateTime.now(),
                game.isDraw()
        ));
    }
}
