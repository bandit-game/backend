package be.kdg.integration5.checkersachievementcontext.adapter.in;

import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleLobbyCreationCommand;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleLobbyCreationUseCase;
import be.kdg.integration5.common.events.LobbyCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyCreationListener {
    private static final String LOBBY_QUEUE = "lobby_queue";
    private final Logger logger = LoggerFactory.getLogger(LobbyCreationListener.class);

    private final HandleLobbyCreationUseCase handleLobbyCreationUseCase;

    public LobbyCreationListener(HandleLobbyCreationUseCase handleLobbyCreationUseCase) {
        this.handleLobbyCreationUseCase = handleLobbyCreationUseCase;
    }

    @RabbitListener(queues = LOBBY_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void createGameFromLobby(LobbyCreatedEvent lobbyCreatedEvent) {
        logger.info("Receive lobby created event: {}", lobbyCreatedEvent.lobbyId());
        HandleLobbyCreationCommand handleLobbyCreationCommand = new HandleLobbyCreationCommand(
                new GameId(lobbyCreatedEvent.lobbyId()),
                lobbyCreatedEvent.players().stream().map(player -> new PlayerId(player.playerId())).toList()
        );
        handleLobbyCreationUseCase.handleLobbyCreation(handleLobbyCreationCommand);
    }
}
