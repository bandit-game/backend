package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.exception.GameNotFoundException;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.move.MoveJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.move.MoveJpaRepository;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player.PlayerJpaRepository;
import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.port.out.FindGamePort;
import be.kdg.integration5.checkersachievementcontext.port.out.PersistGamePort;
import be.kdg.integration5.checkersachievementcontext.domain.Game;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GameDatabaseAdapter implements PersistGamePort, FindGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final GameJpaConverter gameJpaConverter;

    private final PlayerJpaRepository playerJpaRepository;
    private final MoveJpaRepository moveJpaRepository;

    public GameDatabaseAdapter(GameJpaRepository gameJpaRepository, GameJpaConverter gameJpaConverter, PlayerJpaRepository playerJpaRepository, MoveJpaRepository moveJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
        this.gameJpaConverter = gameJpaConverter;
        this.playerJpaRepository = playerJpaRepository;
        this.moveJpaRepository = moveJpaRepository;
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);
        List<MoveJpaEntity> moves = gameJpaEntity.getMoves();

        playerJpaRepository.saveAll(gameJpaEntity.getPlayers());

        gameJpaEntity.setMoves(null);
        GameJpaEntity savedGameJpaEntity = gameJpaRepository.save(gameJpaEntity);

        List<MoveJpaEntity> savedMoveJpaEntities = moveJpaRepository.saveAll(moves);
        savedGameJpaEntity.setMoves(savedMoveJpaEntities);

        return gameJpaConverter.toDomain(savedGameJpaEntity);
    }

//    @Override
//    public Game update(Game game) {
//        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);
//        GameJpaEntity referencedGame = gameJpaRepository.getReferenceById(gameJpaEntity.getGameId());
//
//        referencedGame.setMoves(gameJpaEntity.getMoves());
//        referencedGame.setPlayers(gameJpaEntity.getPlayers());
//
//        return gameJpaConverter.toDomain(referencedGame);
//    }


    @Override
    public Game findById(GameId gameId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByIdFetched(gameId.uuid()).orElseThrow(
                () -> new GameNotFoundException("Game with given id [%s] is not found".formatted(gameId.uuid()))
        );
        return gameJpaConverter.toDomain(gameJpaEntity);
    }
}
