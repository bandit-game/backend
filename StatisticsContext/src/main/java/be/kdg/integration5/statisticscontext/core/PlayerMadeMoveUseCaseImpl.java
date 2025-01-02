package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.PlayerMoveEvent;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import be.kdg.integration5.statisticscontext.port.in.PlayerMadeMoveUseCase;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.FindSessionPort;
import be.kdg.integration5.statisticscontext.port.out.PersistMovePort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class PlayerMadeMoveUseCaseImpl implements PlayerMadeMoveUseCase {

    private final FindSessionPort findSessionPort;
    private final PersistSessionPort persistSessionPort;

    public PlayerMadeMoveUseCaseImpl(FindSessionPort findSessionPort, PersistSessionPort persistSessionPort) {
        this.findSessionPort = findSessionPort;
        this.persistSessionPort = persistSessionPort;
    }

    @Override
    public void saveMove(PlayerMoveEvent event) {
        Session session = findSessionPort.findById(new SessionId(event.lobbyId()));

        session.processMove(
                new PlayerId(event.playerId()),
                new PlayerId(event.nextPlayerId()),
                event.timestamp()
        );
        persistSessionPort.update(session);


    }
}
