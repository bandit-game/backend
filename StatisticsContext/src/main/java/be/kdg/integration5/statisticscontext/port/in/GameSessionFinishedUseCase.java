package be.kdg.integration5.statisticscontext.port.in;

import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.statisticscontext.domain.Session;

public interface GameSessionFinishedUseCase {
    Session finishGameSession(FinishGameSessionEvent event);
}
