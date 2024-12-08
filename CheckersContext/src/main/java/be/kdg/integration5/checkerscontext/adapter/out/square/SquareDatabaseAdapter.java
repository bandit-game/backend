package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.exception.SquareNotFoundException;
import be.kdg.integration5.checkerscontext.domain.*;
import be.kdg.integration5.checkerscontext.port.out.FindSquarePort;
import org.springframework.stereotype.Component;

@Component
public class SquareDatabaseAdapter implements FindSquarePort {
    private final SquareJpaRepository squareJpaRepository;

    public SquareDatabaseAdapter(SquareJpaRepository squareJpaRepository) {
        this.squareJpaRepository = squareJpaRepository;
    }

    @Override
    public Square findSquareByGameIdAndXAndY(GameId gameId, int x, int y) {
        SquareJpaEntity squareJpaEntity = squareJpaRepository.findByGameIdAndXAndYFetched(gameId.uuid(), x, y).orElseThrow(
                () -> new SquareNotFoundException("Square with given id [%s] not found".formatted(new SquareJpaEntityId(gameId.uuid(), x, y)))
        );
        Square square = squareJpaEntity.toDomain();
        Board board = squareJpaEntity.getBoard().toDomain();
        Game game = squareJpaEntity.getBoard().getGame().toDomain();
        board.setGame(game);
        square.setBoard(board);

        Piece piece = squareJpaEntity.getPlacedPiece().toDomain();
        piece.setSquare(square);

        return square;
    }
}
