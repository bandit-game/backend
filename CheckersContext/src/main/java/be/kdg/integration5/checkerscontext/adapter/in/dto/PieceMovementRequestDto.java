package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.Objects;
import java.util.UUID;

public record PieceMovementRequestDto(UUID gameId, UUID playerId, Integer currentX, Integer currentY, Integer targetX, Integer targetY) {
    public PieceMovementRequestDto {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(currentX);
        Objects.requireNonNull(currentY);
        Objects.requireNonNull(targetX);
        Objects.requireNonNull(targetY);
    }
}
