package be.kdg.integration5.common.events;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record PlayerMoveEvent(UUID gameId, UUID playerId, UUID nextPlayerId, LocalDateTime timestamp) {
    public PlayerMoveEvent {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(nextPlayerId);
        Objects.requireNonNull(timestamp);
    }
}
