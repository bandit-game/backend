package be.kdg.integration5.statisticscontext.adapter.in;

import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.common.events.PlayerMoveEvent;
import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.statisticscontext.port.in.GameSessionFinishedUseCase;
import be.kdg.integration5.statisticscontext.port.in.GameSessionStartedUseCase;
import be.kdg.integration5.statisticscontext.port.in.PlayerMadeMoveUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GameSessionListener {

    private static final String GAME_START_QUEUE = "game_start_queue";
    private static final String GAME_END_QUEUE = "game_end_queue";
    private static final String PLAYER_MOVE_QUEUE = "player_move_queue";

    private final GameSessionStartedUseCase gameSessionStartedUseCase;
    private final PlayerMadeMoveUseCase playerMadeMoveUseCase;
    private final GameSessionFinishedUseCase gameSessionFinishedUseCase;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GameSessionListener(GameSessionStartedUseCase gameSessionStartedUseCase, PlayerMadeMoveUseCase playerMadeMoveUseCase, GameSessionFinishedUseCase gameSessionFinishedUseCase) {
        this.gameSessionStartedUseCase = gameSessionStartedUseCase;
        this.playerMadeMoveUseCase = playerMadeMoveUseCase;
        this.gameSessionFinishedUseCase = gameSessionFinishedUseCase;
    }

    @RabbitListener(queues = GAME_START_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void processGameStart(StartGameSessionEvent event) {
        logger.info("Received start game session {}", event.lobbyId());
        gameSessionStartedUseCase.startGame(event);
    }

    @RabbitListener(queues = PLAYER_MOVE_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void processPlayerMove(PlayerMoveEvent event) {
        logger.info("Received player move event by {}", event.playerId());
        playerMadeMoveUseCase.saveMove(event);
    }

    @RabbitListener(queues = GAME_END_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void processGameEnd(FinishGameSessionEvent event) {
        logger.info("Received end game session {}", event.gameId());
        gameSessionFinishedUseCase.finishGameSession(event);
    }

}
