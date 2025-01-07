package be.kdg.integration5.guessitcontext.service;

import be.kdg.integration5.guessitcontext.domain.Move;
import be.kdg.integration5.guessitcontext.domain.MoveResult;
import be.kdg.integration5.guessitcontext.domain.Player;
import be.kdg.integration5.guessitcontext.reposiotory.MoveRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MoveService {

    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Transactional
    public Move save(MoveResult moveResult, Player player, LocalDateTime timestamp) {
        Move move = new Move(
                UUID.randomUUID(),
                player,
                moveResult.resultType().getMessage(),
                timestamp
        );
        return moveRepository.save(move);
    }
}
