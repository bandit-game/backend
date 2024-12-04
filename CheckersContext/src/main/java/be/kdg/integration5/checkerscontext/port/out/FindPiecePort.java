package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Piece;

public interface FindPiecePort {
    Piece findPieceByGameIdAndPlayedSquareNumber(GameId gameId, int playedSquareNumber);
}
