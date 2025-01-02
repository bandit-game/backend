package be.kdg.integration5.statisticscontext.port.in;

import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.statisticscontext.domain.Session;

public interface GameSessionStartedUseCase {
    Session startGame(StartGameSessionEvent startGameSessionEvent);
}
