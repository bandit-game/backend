package be.kdg.integration5.gameplatformcontext.adapter.in.websocket.dto;

import java.util.Objects;
import java.util.UUID;

public record LobbyReadinessDTO(UUID lobbyId) {
    public LobbyReadinessDTO {
        Objects.requireNonNull(lobbyId);
    }
}
