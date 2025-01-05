package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.*;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindAllPossibleMovesUseCaseImpl implements FindAllPossibleMovesUseCase {
    private final FindGamePort findGamePort;
    private final NotifyPlayerPort notifyPlayerPort;

    @Autowired
    public FindAllPossibleMovesUseCaseImpl(FindGamePort findGamePort, NotifyPlayerPort notifyPlayerPort) {
        this.findGamePort = findGamePort;
        this.notifyPlayerPort = notifyPlayerPort;
    }

    @Override
    public List<Move> findAllPossibleMoves(FindAllPossibleMovesCommand findAllPossibleMovesCommand) {
        GameId gameId = findAllPossibleMovesCommand.gameId();
        int x = findAllPossibleMovesCommand.x();
        int y = findAllPossibleMovesCommand.y()
                ;
        Game game = findGamePort.findById(gameId);

        if (game.isFinished())
            return List.of();

        Player currentPlayer = game.getBoard().getCurrentPlayer();
        Board board = game.getBoard();
        Square targetSquare = board.getSquares()[y][x];

        if (targetSquare.isEmpty())
            return List.of();

        Piece piece = targetSquare.getPlacedPiece();

        List<Move> moves = board.getPossibleMoves(piece);
        notifyPlayerPort.notifyPossibleMovesForPlayer(moves, currentPlayer);

        return moves;
    }
}
