package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.port.out.BoardJpaRepository;
import be.kdg.integration5.checkerscontext.port.out.DeleteGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.springframework.stereotype.Component;

@Component
public class GameDatabaseAdapter implements PersistGamePort, DeleteGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final PlayerJpaRepository playerJparepository;

    public GameDatabaseAdapter(GameJpaRepository gameJpaRepository, BoardJpaRepository boardJpaRepository, PlayerJpaRepository playerJparepository) {
        this.gameJpaRepository = gameJpaRepository;
        this.boardJpaRepository = boardJpaRepository;
        this.playerJparepository = playerJparepository;
    }

    @Override
    public Game save(Game game) {
        boardJpaRepository.save(BoardJpaEntity.of(game.getBoard(), game.getPlayedMatchId()));
        playerJparepository.saveAll(game.getPlayers().stream().map(PlayerJpaEntity::of).toList());
        return gameJpaRepository.save(GameJpaEntity.of(game)).toDomain();
    }

    @Override
    public void deleteById(GameId gameId) {

    }
}
