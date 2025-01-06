package be.kdg.integration5.checkersachievementcontext.port.in;

import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.PiecePosition;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;

public record HandleCheckersMadeMoveCommand(GameId gameId, PlayerId moverId, PiecePosition oldPosition, PiecePosition newPosition) {
}
