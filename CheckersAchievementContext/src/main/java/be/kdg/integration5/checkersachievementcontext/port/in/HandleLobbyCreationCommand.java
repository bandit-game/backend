package be.kdg.integration5.checkersachievementcontext.port.in;

import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;

import java.util.List;

public record HandleLobbyCreationCommand(GameId gameId, List<PlayerId> playerIds) {
}
