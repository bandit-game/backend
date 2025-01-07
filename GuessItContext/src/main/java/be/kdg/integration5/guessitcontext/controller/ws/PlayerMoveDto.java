package be.kdg.integration5.guessitcontext.controller.ws;

import java.util.Objects;
import java.util.UUID;

public record PlayerMoveDto(UUID playerId, Integer guessNumber) {
    public PlayerMoveDto {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(guessNumber);
    }
}
