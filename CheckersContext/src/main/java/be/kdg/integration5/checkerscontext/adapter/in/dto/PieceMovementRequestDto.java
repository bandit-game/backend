package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.Objects;
import java.util.UUID;

public record PieceMovementRequestDto(UUID gameId, UUID playerId, MovePostDto move) {
    public PieceMovementRequestDto {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(move);
    }
}
