package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Piece;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindPiecePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindAllPossibleMovesUseCaseImpl implements FindAllPossibleMovesUseCase {
    private final FindPiecePort findPiecePort;

    @Autowired
    public FindAllPossibleMovesUseCaseImpl(FindPiecePort findPiecePort) {
        this.findPiecePort = findPiecePort;
    }

    @Override
    public List<Move> findAllPossibleMoves(FindAllPossibleMovesCommand findAllPossibleMovesCommand) {
        GameId gameId = findAllPossibleMovesCommand.gameId();
        int playedSquareNumber = findAllPossibleMovesCommand.playedSquareNumber();
        Piece piece = findPiecePort.findPieceByGameIdAndPlayedSquareNumber(gameId, playedSquareNumber);
        return piece.getPossibleMoves();

    }
}
