package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.exception.GameNotFoundException;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaRepository;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.out.DeleteGamePort;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.springframework.stereotype.Component;

@Component
public class GameDatabaseAdapter implements PersistGamePort, DeleteGamePort, FindGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final PlayerJpaRepository playerJparepository;

    private final GameJpaConverter gameJpaConverter;

    public GameDatabaseAdapter(GameJpaRepository gameJpaRepository, PieceJpaRepository pieceJpaRepository, PlayerJpaRepository playerJparepository, GameJpaConverter gameJpaConverter) {
        this.gameJpaRepository = gameJpaRepository;
        this.pieceJpaRepository = pieceJpaRepository;
        this.playerJparepository = playerJparepository;
        this.gameJpaConverter = gameJpaConverter;
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);

        playerJparepository.saveAll(gameJpaEntity.getPlayers());
        pieceJpaRepository.saveAll(gameJpaEntity.getPieces());
        GameJpaEntity savedGameJpaEntity = gameJpaRepository.save(gameJpaEntity);
        return gameJpaConverter.toDomain(savedGameJpaEntity);
    }

    @Override
    public void deleteById(GameId gameId) {
        //TODO Implement
    }

    @Override
    public Game findById(GameId gameId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByIdFetched(gameId.uuid()).orElseThrow(
                () -> new GameNotFoundException("Game with given id [%s] is not found".formatted(gameId.uuid()))
        );
        return gameJpaConverter.toDomain(gameJpaEntity);
    }

    @Override
    public Game findGameByPlayerAndGameEndNull(PlayerId playerId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByPlayerIdAndEndDateNullFetched(playerId.uuid())
                .orElseThrow(() -> new GameNotFoundException("Game for player [%s] not found.]".formatted(playerId.uuid())));

        return gameJpaConverter.toDomain(gameJpaEntity);
    }


}
