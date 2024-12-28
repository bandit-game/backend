package be.kdg.integration5.common.events;

import be.kdg.integration5.common.domain.PlayerMatchHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record FinishGameSessionEvent(UUID gameId, UUID winnerId, LocalDateTime timestamp, boolean isDraw) {
    public FinishGameSessionEvent {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(timestamp);
        if (isDraw && winnerId != null)
            throw new IllegalArgumentException("Winner id must be null if the game ended in Draw.");
    }
}
