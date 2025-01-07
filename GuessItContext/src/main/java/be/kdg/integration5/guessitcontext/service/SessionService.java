package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.common.events.LobbyCreatedEvent;
import be.kdg.integration5.guessitcontext.domain.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session update(Session session);
    Session setNextPlayer(Session session);
    Session createSession(LobbyCreatedEvent event);
    Session findNotFinishedWithPlayer(boolean isFinished, PlayerId playerId);
    Session findById(SessionId sessionId);
    List<UUID> getPlayerUUIDExceptFor(PlayerId playerId, Session session);
    void sendGameSessionStartEvent(Session session);
    void sendPlayerMoveEvent(PlayerId playerId, Session session);
    void sendGameSessionEndEvent(Session session);
}
