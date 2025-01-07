package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.common.events.LobbyCreatedEvent;
import be.kdg.integration5.common.events.PlayerMoveEvent;
import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.guessitcontext.config.MQConfig;
import be.kdg.integration5.guessitcontext.domain.*;
import be.kdg.integration5.guessitcontext.exception.SessionNotFoundException;
import be.kdg.integration5.guessitcontext.reposiotory.PlayerRepository;
import be.kdg.integration5.guessitcontext.reposiotory.SessionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RabbitTemplate rabbitTemplate;

    @Value("${game.name}")
    private String gameName;

    public SessionServiceImpl(SessionRepository sessionRepository, PlayerService playerService, PlayerRepository playerRepository, RabbitTemplate rabbitTemplate) {
        this.sessionRepository = sessionRepository;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public Session update(Session session) {
        Session foundSession = findById(new SessionId(session.getSessionId()));
        foundSession.setWinner(session.getWinner());
        foundSession.setFinished(session.isFinished());
        foundSession.setCurrentPlayer(session.getCurrentPlayer());
        return sessionRepository.save(foundSession);
    }


    @Override
    @Transactional
    public Session createSession(LobbyCreatedEvent event) {
        List<Player> players = event.players()
                .stream()
                .map(pl -> new Player(pl.playerId(), pl.username()))
                .toList();
        playerService.saveAll(players);
        Player firstPlayer = playerRepository.getReferenceById(event.firstPlayerId());
        Session session = new Session(
                firstPlayer,
                event.lobbyId(),
                false,
                false
        );
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public Session findNotFinishedWithPlayer(boolean isFinished, PlayerId playerId) {
        return sessionRepository.findByFinishedAndPlayerCustom(false, playerId.uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session for player %s not found.".formatted(playerId.uuid())));

    }

    @Override
    @Transactional
    public Session findById(SessionId sessionId) {
        return  sessionRepository.findByIdFetched(sessionId.uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session %s not found.".formatted(sessionId.uuid())));
    }

    @Override
    @Transactional
    public List<UUID> getPlayerUUIDExceptFor(PlayerId playerId, Session session) {
        return session.getPlayers().stream().map(Player::getPlayerId).filter(id -> id != playerId.uuid()).toList();
    }

    @Override
    public void sendGameSessionStartEvent(Session session) {
        UUID gameUUID = session.getSessionId();
        final String ROUTING_KEY = String.format("game.%s.started", gameUUID);
        logger.info("Notifying game has started: {}", ROUTING_KEY);

        rabbitTemplate.convertAndSend(
                MQConfig.GAME_EVENTS_EXCHANGE,
                ROUTING_KEY,
                new StartGameSessionEvent(
                        gameName,
                        session.getSessionId(),
                        session.getFirstPlayer().getPlayerId(),
                        session.getPlayers().stream().map(Player::getPlayerId).toList(),
                        LocalDateTime.now()
                )
        );
    }

    @Override
    public void sendPlayerMoveEvent(PlayerId playerId, Session session) {
        UUID gameUUID = session.getSessionId();
        final String ROUTING_KEY = String.format("player.%s.moved", gameUUID);
        logger.info("Notifying a player has made a move has finished: {}", ROUTING_KEY);

        rabbitTemplate.convertAndSend(
                MQConfig.GAME_EVENTS_EXCHANGE,
                ROUTING_KEY,
                new PlayerMoveEvent(
                        gameUUID,
                        playerId.uuid(),
                        session.getCurrentPlayer().getPlayerId(),
                        LocalDateTime.now()
                )
        );
    }


    @Override
    public void sendGameSessionEndEvent(Session session) {
        UUID gameUUID = session.getSessionId();
        final String ROUTING_KEY = String.format("game.%s.finished", gameUUID);
        logger.info("Notifying game has finished: {}", ROUTING_KEY);

        rabbitTemplate.convertAndSend(
                MQConfig.GAME_EVENTS_EXCHANGE,
                ROUTING_KEY,
                new FinishGameSessionEvent(
                        gameUUID,
                        session.getWinner() != null ? session.getWinner().getPlayerId() : null,
                        LocalDateTime.now(),
                        session.isDraw()
                ));
    }

    @Override
    public Session setNextPlayer(Session session) {
        if (session.getPlayers() == null || session.getPlayers().isEmpty()) {
            throw new IllegalStateException("Players list is empty or null.");
        }

        Player nextPlayer = session.getPlayers().stream()
                .filter(player -> !player.getPlayerId().equals(session.getCurrentPlayer().getPlayerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No eligible next player found."));

        session.setCurrentPlayer(nextPlayer);
        return session;
    }
}
