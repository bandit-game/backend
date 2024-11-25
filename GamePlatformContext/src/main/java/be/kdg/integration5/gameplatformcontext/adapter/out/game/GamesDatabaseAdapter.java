package be.kdg.integration5.gameplatformcontext.adapter.out.game;

import be.kdg.integration5.gameplatformcontext.adapter.out.exception.GameNotFoundException;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.port.out.FindGamePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GamesDatabaseAdapter implements FindGamePort {
    private final GameJpaRepository gameJpaRepository;

    @Autowired
    public GamesDatabaseAdapter(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
    }

    @Override
    public List<Game> findAllGames() {
        return gameJpaRepository.findAll().stream().map(GameJpaEntity::toDomain).toList();
    }

    @Override
    public Game findGameById(GameId gameId) {
        return gameJpaRepository.findById(gameId.uuid()).orElseThrow(
                () -> new GameNotFoundException("Game with Id[%s] was not found.")).toDomain();
    }
}
