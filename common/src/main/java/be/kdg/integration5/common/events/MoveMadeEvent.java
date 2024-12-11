package be.kdg.integration5.common.events;

import java.util.Objects;
import java.util.UUID;

public record MoveMadeEvent(UUID gameId, UUID playerId, Integer previousX, Integer previousY, Integer newX, Integer newY) {
    public MoveMadeEvent {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(previousX);
        Objects.requireNonNull(previousY);
        Objects.requireNonNull(newX);
        Objects.requireNonNull(newY);
    }
}
