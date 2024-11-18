package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

import java.util.Objects;

public record FindQuickMatchCommand(PlayerId playerId, GameId gameId) {
    public FindQuickMatchCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(gameId);
    }
}
