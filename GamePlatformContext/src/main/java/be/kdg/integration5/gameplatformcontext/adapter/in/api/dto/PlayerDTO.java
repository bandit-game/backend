package be.kdg.integration5.gameplatformcontext.adapter.in.api.dto;

import be.kdg.integration5.gameplatformcontext.domain.Player;

import java.util.Objects;
import java.util.UUID;

public record PlayerDTO(UUID id, String username) {
    public PlayerDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(username);
    }

    public static PlayerDTO of(Player player) {
        return new PlayerDTO(player.getPlayerId().uuid(), player.getUsername());
    }
}
