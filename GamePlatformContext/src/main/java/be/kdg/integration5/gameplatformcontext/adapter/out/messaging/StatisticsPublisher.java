package be.kdg.integration5.gameplatformcontext.adapter.out.messaging;

import be.kdg.integration5.common.events.statistics.NewGameRegisteredEvent;
import be.kdg.integration5.common.events.statistics.NewPlayerRegisteredEvent;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.port.out.SendGamePort;
import be.kdg.integration5.gameplatformcontext.port.out.SendUserInfoPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StatisticsPublisher implements SendUserInfoPort, SendGamePort {

    private static final String STATISTICS_EXCHANGE = "statistics_events";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    public StatisticsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendUserInfo(Player player, String country, String city) {
        UUID playerId = player.getPlayerId().uuid();
        final String routingKey = String.format("player.%s.registered", playerId);
        logger.info("Sending new player registered event: {}", routingKey);
        rabbitTemplate.convertAndSend(STATISTICS_EXCHANGE, routingKey, new NewPlayerRegisteredEvent(
                playerId,
                player.getUsername(),
                player.getGender().name(),
                player.getAge(),
                country,
                city
                ));

    }

    @Override
    public void sendGame(Game game) {
        UUID gameId = game.getGameId().uuid();
        final String routingKey = String.format("game.%s.registered", gameId);
        logger.info("Sending new game registered event: {}", routingKey);
        rabbitTemplate.convertAndSend(STATISTICS_EXCHANGE, routingKey, new NewGameRegisteredEvent(
                gameId,
                game.getTitle()
        ));
    }
}
