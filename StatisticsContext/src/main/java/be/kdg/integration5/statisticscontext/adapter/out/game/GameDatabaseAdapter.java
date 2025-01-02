package be.kdg.integration5.statisticscontext.adapter.out.game;

import be.kdg.integration5.statisticscontext.adapter.out.exception.GameNotFoundException;
import be.kdg.integration5.statisticscontext.domain.Game;
import be.kdg.integration5.statisticscontext.port.out.FindGamePort;
import be.kdg.integration5.statisticscontext.port.out.PersistGamePort;
import org.springframework.stereotype.Component;

@Component
public class GameDatabaseAdapter implements FindGamePort, PersistGamePort {

    private final GameJpaRepository gameJpaRepository;
    private final GameJpaConverter gameJpaConverter;

    public GameDatabaseAdapter(GameJpaRepository gameJpaRepository, GameJpaConverter gameJpaConverter) {
        this.gameJpaRepository = gameJpaRepository;
        this.gameJpaConverter = gameJpaConverter;
    }

    @Override
    public Game findByName(String name) {
        return gameJpaConverter.toDomain(
                gameJpaRepository.findByName(name)
                        .orElseThrow(
                                () -> new GameNotFoundException(name)));
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);
        GameJpaEntity savedGameJpaEntity = gameJpaRepository.save(gameJpaEntity);
        return gameJpaConverter.toDomain(savedGameJpaEntity);
    }
}
