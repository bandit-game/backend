package be.kdg.integration5.statisticscontext.adapter.out.session;

import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionId;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaRepository;
import be.kdg.integration5.statisticscontext.domain.PlayerActivity;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SessionDatabaseAdapter implements PersistSessionPort {

    private final SessionJpaRepository sessionJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private final GameJpaRepository gameJpaRepository;
    private final PlayerSessionJpaRepository playerSessionJpaRepository;
    private final SessionJpaConverter sessionJpaConverter;

    public SessionDatabaseAdapter(SessionJpaRepository sessionJpaRepository, PlayerJpaRepository playerJpaRepository, GameJpaRepository gameJpaRepository, PlayerSessionJpaRepository playerSessionJpaRepository, SessionJpaConverter sessionJpaConverter) {
        this.sessionJpaRepository = sessionJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.gameJpaRepository = gameJpaRepository;
        this.playerSessionJpaRepository = playerSessionJpaRepository;
        this.sessionJpaConverter = sessionJpaConverter;
    }

    @Override
    public Session save(Session session) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.getReferenceById(session.getGame().gameId().uuid());
        SessionJpaEntity sessionJpaEntity = sessionJpaConverter.toJpa(session);
        sessionJpaEntity.setGame(gameJpaEntity);

        SessionJpaEntity savedSessionJpaEntity = sessionJpaRepository.save(sessionJpaEntity);
        List<PlayerSessionJpaEntity> playerSessionJpaEntities = new ArrayList<>();

        for (PlayerActivity playerActivity : session.getActivities()) {
            PlayerJpaEntity playerJpaEntity = playerJpaRepository.getReferenceById(playerActivity.getPlayer().getPlayerId().uuid());
            List<PlayerSessionJpaEntity> currentPlayerSessionJpaEntities = Optional
                    .ofNullable(playerJpaEntity.getSessions())
                    .orElseGet(ArrayList::new);

            PlayerSessionId playerSessionId = new PlayerSessionId(savedSessionJpaEntity.getSessionId(), playerJpaEntity.getPlayerId());

            PlayerSessionJpaEntity playerSessionJpaEntity = new PlayerSessionJpaEntity(playerSessionId);
            playerSessionJpaEntity.setSession(savedSessionJpaEntity);
            playerSessionJpaEntity.setPlayer(playerJpaEntity);

            PlayerSessionJpaEntity savedPlayerSessionJpaEntity = playerSessionJpaRepository.save(playerSessionJpaEntity);
            playerSessionJpaEntities.add(savedPlayerSessionJpaEntity);

            currentPlayerSessionJpaEntities.add(savedPlayerSessionJpaEntity);
            playerJpaEntity.setSessions(currentPlayerSessionJpaEntities);
        }

        sessionJpaEntity.setPlayers(playerSessionJpaEntities);

        return sessionJpaConverter.toDomain(sessionJpaEntity);
    }
}
