package be.kdg.integration5.statisticscontext.port.in;

import be.kdg.integration5.common.events.StartGameSessionEvent;

public interface GameSessionStartedUseCase {
    void startGame(StartGameSessionEvent startGameSessionEvent);
}
