package be.kdg.integration5.checkerscontext.adapter.out.piece;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Piece;
import be.kdg.integration5.checkerscontext.port.out.FindPiecePort;
import org.springframework.stereotype.Component;


// TODO
//  Might need it later
//@Component
public class PieceDatabaseAdapter implements FindPiecePort {
    private final PieceJpaRepository pieceJpaRepository;

    public PieceDatabaseAdapter(PieceJpaRepository pieceJpaRepository) {
        this.pieceJpaRepository = pieceJpaRepository;
    }

    @Override
    public Piece findPieceByGameIdAndPlayedSquareNumber(GameId gameId, int x, int y) {
        return null;
    }
}
