package be.kdg.integration5.checkerscontext.adapter.out.dto;

import java.util.Objects;
import java.util.UUID;

public record PlayerResponseDto(UUID playerId, String username, String color) {
    public PlayerResponseDto {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(username);
        Objects.requireNonNull(color);
    }
}
