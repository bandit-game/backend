package be.kdg.integration5.statisticscontext.adapter.in.messaging;

import be.kdg.integration5.common.events.statistics.NewGameRegisteredEvent;
import be.kdg.integration5.common.events.statistics.NewPlayerRegisteredEvent;
import be.kdg.integration5.statisticscontext.port.in.RegisterNewGameUseCase;
import be.kdg.integration5.statisticscontext.port.in.RegisterNewPlayerUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GamePlatformListener {

    private static final String NEW_PLAYER_QUEUE = "new_player_queue";
    private static final String NEW_GAME_QUEUE = "new_game_queue";

    private final RegisterNewPlayerUseCase registerNewPlayerUseCase;
    private final RegisterNewGameUseCase registerNewGameUseCase;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GamePlatformListener(RegisterNewPlayerUseCase registerNewPlayerUseCase, RegisterNewGameUseCase registerNewGameUseCase) {
        this.registerNewPlayerUseCase = registerNewPlayerUseCase;
        this.registerNewGameUseCase = registerNewGameUseCase;
    }

    @RabbitListener(queues = NEW_PLAYER_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void processNewPlayerRegistered(NewPlayerRegisteredEvent event) {
        logger.info("New player registered on platform {}", event.playerId());
        registerNewPlayerUseCase.register(event);
    }

    @RabbitListener(queues = NEW_GAME_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void processNewGameRegistered(NewGameRegisteredEvent event) {
        logger.info("New game registered on platform {}", event.gameId());
        registerNewGameUseCase.register(event);

    }
}
