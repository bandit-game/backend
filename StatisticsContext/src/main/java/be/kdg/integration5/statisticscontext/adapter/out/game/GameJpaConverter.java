package be.kdg.integration5.statisticscontext.adapter.out.game;

import be.kdg.integration5.statisticscontext.domain.Game;
import be.kdg.integration5.statisticscontext.domain.GameId;
import org.springframework.stereotype.Component;

@Component
public class GameJpaConverter {

    public GameJpaEntity toJpa(Game game) {
        return new GameJpaEntity(game.gameId().uuid(), game.name());
    }
    public Game toDomain(GameJpaEntity entity) {
        return new Game(new GameId(entity.getGameId()), entity.getName());
    }
}
