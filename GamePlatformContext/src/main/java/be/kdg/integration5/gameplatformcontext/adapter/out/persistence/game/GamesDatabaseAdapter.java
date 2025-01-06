package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.game;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.exception.GameNotFoundException;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.port.out.FindGamePort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistGamePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GamesDatabaseAdapter implements FindGamePort, PersistGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final GameJpaConverter gameJpaConverter;

    @Autowired
    public GamesDatabaseAdapter(GameJpaRepository gameJpaRepository, GameJpaConverter gameJpaConverter) {
        this.gameJpaRepository = gameJpaRepository;
        this.gameJpaConverter = gameJpaConverter;
    }

    @Override
    public List<Game> findAllGames() {
        return gameJpaRepository.findAll().stream().map(gameJpaConverter::toDomain).toList();
    }

    @Override
    public Game findGameById(GameId gameId) {
        return gameJpaConverter.toDomain(gameJpaRepository.findById(gameId.uuid()).orElseThrow(
                () -> new GameNotFoundException("Game with Id[%s] was not found.")));
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);
        GameJpaEntity savedJpaEntity = gameJpaRepository.save(gameJpaEntity);
        return gameJpaConverter.toDomain(savedJpaEntity);
    }
}
