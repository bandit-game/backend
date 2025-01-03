package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import be.kdg.integration5.statisticscontext.port.in.GameSessionFinishedUseCase;
import be.kdg.integration5.statisticscontext.port.out.FetchPredictionPort;
import be.kdg.integration5.statisticscontext.port.out.FindSessionPort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameSessionFinishedUseCaseImpl implements GameSessionFinishedUseCase {

    private final PersistSessionPort persistSessionPort;
    private final FindSessionPort findSessionPort;
    private final FetchPredictionPort fetchPredictionPort;

    public GameSessionFinishedUseCaseImpl(PersistSessionPort persistSessionPort, FindSessionPort findSessionPort, FetchPredictionPort fetchPredictionPort) {
        this.persistSessionPort = persistSessionPort;
        this.findSessionPort = findSessionPort;
        this.fetchPredictionPort = fetchPredictionPort;
    }

    @Override
    public Session finishGameSession(FinishGameSessionEvent event) {
        Session session = findSessionPort.findById(new SessionId(event.gameId()));

        session.gameFinish(
                new PlayerId(event.winnerId()),
                event.timestamp(),
                event.isDraw()
        );

        return persistSessionPort.update(session);
    }
}
