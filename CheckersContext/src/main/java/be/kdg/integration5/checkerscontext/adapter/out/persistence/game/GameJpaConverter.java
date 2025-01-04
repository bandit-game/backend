package be.kdg.integration5.checkerscontext.adapter.out.persistence.game;

import be.kdg.integration5.checkerscontext.adapter.out.persistence.exception.GameConversionException;
import be.kdg.integration5.checkerscontext.adapter.out.persistence.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.persistence.piece.PieceJpaEntityId;
import be.kdg.integration5.checkerscontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GameJpaConverter {
    public GameJpaEntity toJpa(Game game) {
        UUID gameId = game.getPlayedMatchId().uuid();
        GameJpaEntity gameJpaEntity = new GameJpaEntity(
                gameId,
                game.isFinished(),
                game.getPlayers().stream().map(PlayerJpaEntity::of).collect(Collectors.toSet()) ,
                PlayerJpaEntity.of(game.getBoard().getCurrentPlayer())
        );

        List<Piece> pieces = game.getBoard().getPieces();
        Set<PieceJpaEntity> pieceJpaEntities = pieces.stream().map(piece -> new PieceJpaEntity(
                new PieceJpaEntityId(gameId, piece.getPiecePosition().x(), piece.getPiecePosition().y()),
                gameJpaEntity,
                piece.isKing(),
                piece.getColor(),
                PlayerJpaEntity.of(piece.getOwner())
        )).collect(Collectors.toSet());

        gameJpaEntity.setPieces(pieceJpaEntities);

        return gameJpaEntity;
    }

    public Game toDomain(GameJpaEntity gameJpaEntity) {
        List<PieceJpaEntity> pieceJpaEntities = gameJpaEntity.getPieces().stream().toList();
        if (pieceJpaEntities.isEmpty())
            throw new GameConversionException("PieceJpaEntities is null or empty.");

        Set<PlayerJpaEntity> playerJpaEntities = gameJpaEntity.getPlayers();
        if (playerJpaEntities == null || playerJpaEntities.isEmpty())
            throw new GameConversionException("PlayerJpaEntities is null or empty.");


        List<Piece> pieces = pieceJpaEntities.stream().map(pieceJpaEntity -> {
                    PieceJpaEntityId pieceId = pieceJpaEntity.getPieceId();
                    return new Piece(
                            new PiecePosition(
                                    pieceId.getCurrentX(),
                                    pieceId.getCurrentY()
                            ),
                            pieceJpaEntity.isKing(),
                            pieceJpaEntity.getPieceColor(),
                            pieceJpaEntity.getOwner().toDomain()
                    );
                }
        ).toList();

        List<Player> players = playerJpaEntities.stream().map(PlayerJpaEntity::toDomain).toList();
        Player currentPlayer = gameJpaEntity.getCurrentPlayer().toDomain();
        Board board = new Board(pieces, players, currentPlayer);

        UUID gameUUID = pieceJpaEntities.getFirst().getPieceId().getGameId();
        GameId gameId = new GameId(gameUUID);

        return new Game(gameId, board, players);
    }
}
