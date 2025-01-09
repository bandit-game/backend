package be.kdg.integration5.statisticscontext.port.in;

import be.kdg.integration5.common.events.statistics.NewGameRegisteredEvent;
import be.kdg.integration5.statisticscontext.domain.Game;

public interface RegisterNewGameUseCase {
    Game register(NewGameRegisteredEvent event);
}
