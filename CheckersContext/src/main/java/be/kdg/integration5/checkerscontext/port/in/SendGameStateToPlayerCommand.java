package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;

import java.util.Objects;

public record SendGameStateToPlayerCommand(GameId gameId, PlayerId playerId) {
    public SendGameStateToPlayerCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
    }
}
