package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.*;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.FindPiecePort;
import be.kdg.integration5.checkerscontext.port.out.FindSquarePort;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindAllPossibleMovesUseCaseImpl implements FindAllPossibleMovesUseCase {
    private final FindSquarePort findSquarePort;
    private final FindGamePort findGamePort;
    private final NotifyPlayerPort notifyPlayerPort;

    @Autowired
    public FindAllPossibleMovesUseCaseImpl(FindSquarePort findSquarePort, FindGamePort findGamePort, NotifyPlayerPort notifyPlayerPort) {
        this.findSquarePort = findSquarePort;
        this.findGamePort = findGamePort;
        this.notifyPlayerPort = notifyPlayerPort;
    }

    @Override
    public List<Move> findAllPossibleMoves(FindAllPossibleMovesCommand findAllPossibleMovesCommand) {
        GameId gameId = findAllPossibleMovesCommand.gameId();
        Game game = findGamePort.findById(gameId);
        Player currentPlayer = game.getBoard().getCurrentPlayer();

        int x = findAllPossibleMovesCommand.x();
        int y = findAllPossibleMovesCommand.y();
        Square square = findSquarePort.findSquareByGameIdAndXAndY(gameId, x, y);

        if (square.isEmpty())
            return List.of();

        Piece piece = square.getPlacedPiece();

        List<Move> moves = piece.getPossibleMoves();
        notifyPlayerPort.notifyPossibleMovesForPlayer(moves, currentPlayer);

        return piece.getPossibleMoves();
    }
}
