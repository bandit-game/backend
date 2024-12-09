package be.kdg.integration5.checkerscontext.port.in;

import java.util.List;
import java.util.UUID;

public record CreateGameCommand(UUID lobbyId, List<PlayerJoinedCommand> playersJoined) {
    public record PlayerJoinedCommand(UUID playerId, String playerName, Boolean isFirst) {

    }
}
