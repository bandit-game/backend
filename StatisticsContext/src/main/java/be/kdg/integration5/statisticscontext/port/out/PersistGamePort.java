package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Game;

public interface PersistGamePort {
    Game save(Game game);
}
