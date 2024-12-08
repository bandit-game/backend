package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Piece;
import be.kdg.integration5.checkerscontext.domain.Square;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindPiecePort;
import be.kdg.integration5.checkerscontext.port.out.FindSquarePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindAllPossibleMovesUseCaseImpl implements FindAllPossibleMovesUseCase {
    private final FindSquarePort findSquarePort;

    @Autowired
    public FindAllPossibleMovesUseCaseImpl(FindSquarePort findSquarePort) {
        this.findSquarePort = findSquarePort;
    }

    @Override
    public List<Move> findAllPossibleMoves(FindAllPossibleMovesCommand findAllPossibleMovesCommand) {
        GameId gameId = findAllPossibleMovesCommand.gameId();
        int x = findAllPossibleMovesCommand.x();
        int y = findAllPossibleMovesCommand.y();
        Square square = findSquarePort.findSquareByGameIdAndXAndY(gameId, x, y);

        if (square.isEmpty())
            return List.of();

        Piece piece = square.getPlacedPiece();
        return piece.getPossibleMoves();
    }
}
