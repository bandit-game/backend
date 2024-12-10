package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.Board;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;

import java.util.Objects;

public record MovePieceCommand(GameId gameId, PlayerId playerId, int currentX, int currentY, int targetX, int targetY) {
    public MovePieceCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        if (currentX < 0 || targetX < 0 || currentY < 0 || targetY < 0 ||
                currentX >= Board.BOARD_SIZE || currentY >= Board.BOARD_SIZE ||
                targetX >= Board.BOARD_SIZE || targetY >= Board.BOARD_SIZE) {
            throw new IllegalArgumentException("Coordinates must be between 0 and " + (Board.BOARD_SIZE - 1));
        }
    }
}
