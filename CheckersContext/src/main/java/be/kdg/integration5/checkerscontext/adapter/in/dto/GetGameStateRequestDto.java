package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.Objects;
import java.util.UUID;

public record GetGameStateRequestDto(UUID gameId, UUID playerId) {
    public GetGameStateRequestDto {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
    }
}
