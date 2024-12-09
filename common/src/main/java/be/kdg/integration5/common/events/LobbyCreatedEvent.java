package be.kdg.integration5.common.events;

import java.util.List;
import java.util.UUID;

public record LobbyCreatedEvent(UUID lobbyId, UUID firstPlayerId, List<PlayerEvent> players) {

    public record PlayerEvent(UUID playerId, String username) {

    }
}
