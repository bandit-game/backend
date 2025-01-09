package be.kdg.integration5.gameplatformcontext.adapter.in.websocket.dto;

import java.util.Objects;
import java.util.UUID;

public record JoinLobbyRequestDTO(UUID playerId, UUID gameId) {
    public JoinLobbyRequestDTO {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(gameId);
    }
}
