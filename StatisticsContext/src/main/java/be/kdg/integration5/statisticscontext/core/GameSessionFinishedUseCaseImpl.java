package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.statisticscontext.domain.*;
import be.kdg.integration5.statisticscontext.port.in.GameSessionFinishedUseCase;
import be.kdg.integration5.statisticscontext.port.out.FetchPredictionPort;
import be.kdg.integration5.statisticscontext.port.out.FindSessionPort;
import be.kdg.integration5.statisticscontext.port.out.PersistPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameSessionFinishedUseCaseImpl implements GameSessionFinishedUseCase {

    private final PersistSessionPort persistSessionPort;
    private final FindSessionPort findSessionPort;
    private final FetchPredictionPort fetchPredictionPort;
    private final PersistPlayerPort persistPlayerPort;

    public GameSessionFinishedUseCaseImpl(PersistSessionPort persistSessionPort, FindSessionPort findSessionPort, FetchPredictionPort fetchPredictionPort, PersistPlayerPort persistPlayerPort) {
        this.persistSessionPort = persistSessionPort;
        this.findSessionPort = findSessionPort;
        this.fetchPredictionPort = fetchPredictionPort;
        this.persistPlayerPort = persistPlayerPort;
    }

    @Override
    public Session finishGameSession(FinishGameSessionEvent event) {
        Session session = findSessionPort.findById(new SessionId(event.gameId()));

        session.gameFinish(
                new PlayerId(event.winnerId()),
                event.timestamp(),
                event.isDraw()
        );
        Session finishedSession = persistSessionPort.update(session);

        List<Player> updatedPlayers = fetchPredictionPort.fetchPredictions(session.getPlayers())
                .entrySet()
                .stream()
                .map(entry -> {
                    Player player = entry.getKey();
                    player.setPredictions(entry.getValue());
                    return player;
                })
                .collect(Collectors.toList());

        persistPlayerPort.updateAll(updatedPlayers);

        return finishedSession;
    }
}
