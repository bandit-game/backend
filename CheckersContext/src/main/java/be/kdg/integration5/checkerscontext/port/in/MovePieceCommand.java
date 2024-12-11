package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.*;
import com.rabbitmq.client.Command;

import java.util.Objects;

public record MovePieceCommand(GameId gameId, PlayerId playerId, Move move) {
    public MovePieceCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(move);
    }
}
