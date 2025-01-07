package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.guessitcontext.domain.*;
import be.kdg.integration5.guessitcontext.exception.IncorrectPlayerTurnException;
import be.kdg.integration5.guessitcontext.exception.SessionNotFoundException;
import be.kdg.integration5.guessitcontext.reposiotory.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class GameService {
    private final SessionRepository sessionRepository;
    private final MoveService moveService;
    private final SessionService sessionService;


    public GameService(SessionRepository sessionRepository, MoveService moveService, SessionService sessionService) {
        this.sessionRepository = sessionRepository;
        this.moveService = moveService;
        this.sessionService = sessionService;

    }

    @Transactional
    public MoveResult makeMove(PlayerId playerId, Integer guess) {
        Session session = sessionRepository.findByFinishedAndPlayerCustom(false, playerId.uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session for player %s not found.".formatted(playerId.uuid())));

        if (!session.getCurrentPlayer().getPlayerId().equals(playerId.uuid()))
            throw new IncorrectPlayerTurnException("It is not %s player turn.".formatted(playerId.uuid()));

        MoveResult moveResult = checkGuess(guess, session.getTargetNumber());

        if (moveResult.isFinished())
            session.setFinished(true);


        moveService.save(moveResult, session.getCurrentPlayer(), LocalDateTime.now());
        sessionService.updateNextPlayer(session);

        return moveResult;
    }

    public int getTargetNumber(SessionId sessionId) {
        Session session = sessionRepository.findById(sessionId.uuid())
                .orElseThrow(() -> new SessionNotFoundException("Session %s not found.".formatted(sessionId.uuid())));
        return session.getTargetNumber();
    }

    private MoveResult checkGuess(int guess, int targetNumber) {
        if (guess < 1 || guess > 10) {
            return new MoveResult(ResultType.INCORRECT, false);
        } else if (guess < targetNumber) {
            return new MoveResult(ResultType.LESS, false);
        } else if (guess > targetNumber) {
            return new MoveResult(ResultType.MORE, false);
        } else {
            return new MoveResult(ResultType.SUCCESS, true);
        }
    }



}
