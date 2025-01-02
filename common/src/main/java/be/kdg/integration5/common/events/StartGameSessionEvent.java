package be.kdg.integration5.common.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record StartGameSessionEvent(String gameName, UUID lobbyId, UUID firstPlayerId, List<UUID> playerIds, LocalDateTime timestamp) {
    public StartGameSessionEvent {
        Objects.requireNonNull(gameName);
        Objects.requireNonNull(lobbyId);
        Objects.requireNonNull(firstPlayerId);
        Objects.requireNonNull(playerIds);
        if (!playerIds.contains(firstPlayerId)) {
            throw new IllegalArgumentException("First player ID must be present in the player IDs list");
        }
    }
}
