package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.GameId;

import java.util.Objects;

public record FindAllPossibleMovesCommand(GameId gameId, Integer squareNumber) {
    public FindAllPossibleMovesCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(squareNumber);
    }
}
