package be.kdg.integration5.checkersachievementcontext.core;

import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleLobbyCreationCommand;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleLobbyCreationUseCase;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandleLobbyCreationUseCaseImpl implements HandleLobbyCreationUseCase {
    @Override
    public void handleLobbyCreation(@NonNull HandleLobbyCreationCommand handleLobbyCreationCommand) {
        GameId gameId = handleLobbyCreationCommand.gameId();

        //TODO
        if (/* check if lobby created for this game?? */ false) {}

        List<PlayerId> playerIds = handleLobbyCreationCommand.playerIds();


    }
}
