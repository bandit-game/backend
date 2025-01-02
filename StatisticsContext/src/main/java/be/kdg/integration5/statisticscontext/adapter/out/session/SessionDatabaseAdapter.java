package be.kdg.integration5.statisticscontext.adapter.out.session;

import be.kdg.integration5.statisticscontext.adapter.out.exception.PlayerSessionNotFoundException;
import be.kdg.integration5.statisticscontext.adapter.out.exception.SessionNotFoundException;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionId;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaRepository;
import be.kdg.integration5.statisticscontext.domain.Move;
import be.kdg.integration5.statisticscontext.domain.PlayerActivity;
import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;
import be.kdg.integration5.statisticscontext.port.out.FindSessionPort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SessionDatabaseAdapter implements PersistSessionPort, FindSessionPort {

    private final SessionJpaRepository sessionJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private final GameJpaRepository gameJpaRepository;
    private final PlayerSessionJpaRepository playerSessionJpaRepository;
    private final MoveJpaRepository moveJpaRepository;

    private final MoveJpaConverter moveJpaConverter;
    private final SessionJpaConverter sessionJpaConverter;

    public SessionDatabaseAdapter(SessionJpaRepository sessionJpaRepository, PlayerJpaRepository playerJpaRepository, GameJpaRepository gameJpaRepository, PlayerSessionJpaRepository playerSessionJpaRepository, MoveJpaRepository moveJpaRepository, MoveJpaConverter moveJpaConverter, SessionJpaConverter sessionJpaConverter) {
        this.sessionJpaRepository = sessionJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.gameJpaRepository = gameJpaRepository;
        this.playerSessionJpaRepository = playerSessionJpaRepository;
        this.moveJpaRepository = moveJpaRepository;
        this.moveJpaConverter = moveJpaConverter;
        this.sessionJpaConverter = sessionJpaConverter;
    }

    @Override
    public Session save(Session session) {
        // Fetch the game entity
        GameJpaEntity gameJpaEntity = gameJpaRepository.getReferenceById(session.getGame().gameId().uuid());

        // Convert domain Session to JPA Session entity and set the game reference
        SessionJpaEntity sessionJpaEntity = sessionJpaConverter.toJpa(session);
        sessionJpaEntity.setGame(gameJpaEntity);

        // Save the session entity
        SessionJpaEntity savedSessionJpaEntity = sessionJpaRepository.save(sessionJpaEntity);

        // Process and save PlayerSession entities
        Set<PlayerSessionJpaEntity> playerSessionJpaEntities = session.getActivities().stream().map(playerActivity -> {
            PlayerJpaEntity playerJpaEntity = playerJpaRepository.getReferenceById(playerActivity.getPlayer().getPlayerId().uuid());
            PlayerSessionId playerSessionId = new PlayerSessionId(savedSessionJpaEntity.getSessionId(), playerJpaEntity.getPlayerId());

            PlayerSessionJpaEntity playerSessionJpaEntity = new PlayerSessionJpaEntity(playerSessionId);
            playerSessionJpaEntity.setPlayer(playerJpaEntity);
            playerSessionJpaEntity.setSession(savedSessionJpaEntity);

            PlayerSessionJpaEntity savedPlayerSessionJpaEntity = playerSessionJpaRepository.save(playerSessionJpaEntity);

            Set<MoveJpaEntity> moveJpaEntities = playerActivity.getMoves().stream().map(move -> {
                MoveJpaEntity moveJpaEntity = new MoveJpaEntity(
                        move.getMoveId().uuid(),
                        move.getMoveNumber(),
                        move.getEndTime(),
                        move.getStartTime()
                );
                moveJpaEntity.setPlayerSession(savedPlayerSessionJpaEntity);
                return moveJpaRepository.save(moveJpaEntity);
            }).collect(Collectors.toSet());

            savedPlayerSessionJpaEntity.setMoves(moveJpaEntities);

            return savedPlayerSessionJpaEntity;
        }).collect(Collectors.toSet());

        // Link PlayerSession entities to the Session entity
        sessionJpaEntity.setPlayers(playerSessionJpaEntities);

        return sessionJpaConverter.toDomain(sessionJpaEntity);
    }

    @Override
    public Session update(Session session) {
        SessionJpaEntity sessionJpaEntity = sessionJpaRepository.findBySessionIdFetched(session.getSessionId().uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session %s not found".formatted(session.getSessionId().uuid())));

        // Update SessionJpaEntity fields
        if (session.getWinner() != null) {
            PlayerJpaEntity winnerJpa = playerJpaRepository.getReferenceById(session.getWinner().getPlayerId().uuid());
            sessionJpaEntity.setWinner(winnerJpa);
        }

        sessionJpaEntity.setDraw(session.isDraw());
        sessionJpaEntity.setFinishTime(session.getFinishTime());

        // Create or update last move of every player in current session
        for (PlayerSessionJpaEntity playerSessionJpaEntity : sessionJpaEntity.getPlayers()) {
            PlayerActivity playerActivity = session.getActivities()
                    .stream()
                    .filter(pa -> pa.getPlayer().getPlayerId().uuid().equals(playerSessionJpaEntity.getPlayer().getPlayerId()))
                    .findFirst()
                    .orElseThrow(() -> new PlayerSessionNotFoundException("Player session linking entity not found."));
            Set<MoveJpaEntity> playerMovesJpa = playerSessionJpaEntity.getMoves();

            Move lastMove = playerActivity.getLastMove();

            MoveJpaEntity moveJpaEntity = moveJpaConverter.toJpa(lastMove);
            moveJpaEntity.setPlayerSession(playerSessionJpaEntity);

            MoveJpaEntity savedMoveJpaEntity = moveJpaRepository.save(moveJpaEntity);

            if (playerMovesJpa.stream()
                    .noneMatch(moveJpa -> moveJpa.getMoveId().equals(lastMove.getMoveId().uuid()))) {
                playerMovesJpa.add(savedMoveJpaEntity);
                playerSessionJpaEntity.setMoves(playerMovesJpa);
            }
        }

        return sessionJpaConverter.toDomain(sessionJpaRepository.save(sessionJpaEntity));
    }

    @Override
    public Session findById(SessionId id) {
        SessionJpaEntity sessionJpaEntity = sessionJpaRepository.findBySessionIdFetched(id.uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session %s not found".formatted(id.uuid())));

        return sessionJpaConverter.toDomain(sessionJpaEntity);
    }
}
