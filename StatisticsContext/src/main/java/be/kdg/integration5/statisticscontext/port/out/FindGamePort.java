package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Game;
import be.kdg.integration5.statisticscontext.domain.GameId;

public interface FindGamePort {

    Game findByName(String name);
}
