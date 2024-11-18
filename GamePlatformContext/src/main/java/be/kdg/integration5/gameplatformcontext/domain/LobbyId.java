package be.kdg.integration5.gameplatformcontext.domain;

import java.util.Objects;
import java.util.UUID;

public record LobbyId(UUID uuid) {
    public LobbyId {
        Objects.requireNonNull(uuid);
    }
}
