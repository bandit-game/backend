package be.kdg.integration5.gameplatformcontext.adapter.in.dto;

import java.util.UUID;

public record JoinLobbyRequestDTO(UUID playerId, UUID gameId) {
}
