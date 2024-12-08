package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.board.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.exception.GameNotFoundException;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.checkerscontext.domain.Board;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.adapter.out.board.BoardJpaRepository;
import be.kdg.integration5.checkerscontext.port.out.DeleteGamePort;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.springframework.stereotype.Component;

@Component
public class GameDatabaseAdapter implements PersistGamePort, DeleteGamePort, FindGamePort {
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
        boardJpaRepository.save(BoardJpaEntity.of(game.getBoard()));
        playerJparepository.saveAll(game.getPlayers().stream().map(PlayerJpaEntity::of).toList());
        return gameJpaRepository.save(GameJpaEntity.of(game)).toDomain();
    }

    @Override
    public void deleteById(GameId gameId) {

    }

    @Override
    public Game findById(GameId gameId) {
        GameJpaEntity foundGame = gameJpaRepository.findByIdFetched(gameId.uuid())
                .orElseThrow(() -> new GameNotFoundException("Game with Id [%s] not found.".formatted(gameId.uuid())));
        Board board = foundGame.getBoard().toDomain();
        Game game = foundGame.toDomain();

        game.setBoard(board);
        return game;
    }
}
