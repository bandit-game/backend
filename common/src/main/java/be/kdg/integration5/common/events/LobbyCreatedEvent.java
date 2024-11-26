package be.kdg.integration5.common.events;

import java.util.List;
import java.util.UUID;

public record LobbyCreatedEvent(UUID lobbyId, List<UUID> playersIds) {
}
