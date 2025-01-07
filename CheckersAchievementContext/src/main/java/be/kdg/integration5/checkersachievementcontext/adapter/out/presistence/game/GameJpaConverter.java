package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.move.MoveJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.move.MoveJpaEntityId;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player.PlayerJpaEntity;
import be.kdg.integration5.checkersachievementcontext.domain.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameJpaConverter {
    public GameJpaEntity toJpa(Game game) {
        UUID gameId = game.getGameId().uuid();
        GameJpaEntity gameJpaEntity = new GameJpaEntity(
                gameId,
                game.getPlayers().stream().map(PlayerJpaEntity::of).toList()
        );
        gameJpaEntity.setMoves(game.getBoard().movesHistory().stream().map(move -> {
            Player mover = move.mover();
            return new MoveJpaEntity(
                    new MoveJpaEntityId(gameId, mover.getPlayerId().uuid(), move.madeAt()),
                    gameJpaEntity,
                    PlayerJpaEntity.of(mover),
                    move.oldPosition().x(),
                    move.oldPosition().y(),
                    move.newPosition().x(),
                    move.newPosition().y()
            );
        }).toList());
        return gameJpaEntity;
    }

    public Game toDomain(GameJpaEntity gameJpaEntity) {
        return new Game(
                new GameId(gameJpaEntity.getGameId()),
                gameJpaEntity.getPlayers().stream().map(PlayerJpaEntity::toDomain).toList(),
                new Board(gameJpaEntity.getMoves().stream().map(moveJpaEntity -> new Move(
                                moveJpaEntity.getMover().toDomain(),
                                new PiecePosition(moveJpaEntity.getOldX(), moveJpaEntity.getOldY()),
                                new PiecePosition(moveJpaEntity.getNewX(), moveJpaEntity.getNewY()),
                                moveJpaEntity.getMoveId().getMovedAt()
                        )).toList())
        );
    }
}
