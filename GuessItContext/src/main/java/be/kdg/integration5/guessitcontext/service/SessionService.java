package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.guessitcontext.domain.Player;
import be.kdg.integration5.guessitcontext.domain.Session;
import be.kdg.integration5.guessitcontext.reposiotory.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public Session updateNextPlayer(Session session) {
        Session updatedSession = this.setNextPlayer(session);
        return sessionRepository.save(updatedSession);
    }


    private Session setNextPlayer(Session session) {
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
