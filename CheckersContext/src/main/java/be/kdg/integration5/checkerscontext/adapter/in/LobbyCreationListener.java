package be.kdg.integration5.checkerscontext.adapter.in;

import be.kdg.integration5.checkerscontext.port.in.CreateGameCommand;
import be.kdg.integration5.checkerscontext.port.in.CreateGameUseCase;
import be.kdg.integration5.common.events.LobbyCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LobbyCreationListener {

    private static final String LOBBY_QUEUE = "lobby_queue";

    private final CreateGameUseCase createGameUseCase;

    public LobbyCreationListener(CreateGameUseCase createGameUseCase) {
        this.createGameUseCase = createGameUseCase;
    }

    @RabbitListener(queues = LOBBY_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void createGameFromLobby(LobbyCreatedEvent event) {
        CreateGameCommand command = new CreateGameCommand(
          event.lobbyId(),
          event.players().stream()
                  .map(pl -> new CreateGameCommand.PlayerJoinedCommand(
                          pl.playerId(),
                          pl.username(),
                    pl.playerId() == event.firstPlayerId()))
                  .collect(Collectors.toList()));

        createGameUseCase.initiate(command);
    }
}
