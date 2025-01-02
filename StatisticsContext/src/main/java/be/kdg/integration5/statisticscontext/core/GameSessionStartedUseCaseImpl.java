package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.statisticscontext.domain.*;
import be.kdg.integration5.statisticscontext.port.in.GameSessionStartedUseCase;
import be.kdg.integration5.statisticscontext.port.out.FindGamePort;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class GameSessionStartedUseCaseImpl implements GameSessionStartedUseCase {

    private final PersistSessionPort persistSessionPort;
    private final FindGamePort findGamePort;
    private final FindPlayerPort findPlayerPort;
    private final Logger logger = LoggerFactory.getLogger(GameSessionStartedUseCaseImpl.class);

    public GameSessionStartedUseCaseImpl(PersistSessionPort persistSessionPort, FindGamePort findGamePort, FindPlayerPort findPlayerPort) {
        this.persistSessionPort = persistSessionPort;
        this.findGamePort = findGamePort;
        this.findPlayerPort = findPlayerPort;
    }

    @Override
    public void startGame(StartGameSessionEvent startGameSessionEvent) {
        String normalizedName = Game.normalizeName(startGameSessionEvent.gameName());
        Game game = findGamePort.findByName(normalizedName);

        List<PlayerId> playerIds = startGameSessionEvent.playerIds().stream().map(PlayerId::new).toList();
        List<Player> players = findPlayerPort.findPlayersByIds(playerIds);

        Session session = new Session(new SessionId(startGameSessionEvent.lobbyId()), game);
        session.start(startGameSessionEvent.timestamp(), players);

        Session savedSession = persistSessionPort.save(session);
        logger.info("Game session saved: {}", savedSession.getSessionId());

    }
}
