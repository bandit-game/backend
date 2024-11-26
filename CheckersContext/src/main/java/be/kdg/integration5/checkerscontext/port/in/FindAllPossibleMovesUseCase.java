package be.kdg.integration5.checkerscontext.port.in;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Square;

import java.util.List;

public interface FindAllPossibleMovesUseCase {
    List<Move> findAllPossibleMoves(FindAllPossibleMovesCommand findAllPossibleMovesCommand);
}
