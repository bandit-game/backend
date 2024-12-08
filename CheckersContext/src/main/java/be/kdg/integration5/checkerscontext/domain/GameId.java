package be.kdg.integration5.checkerscontext.domain;

import java.util.Objects;
import java.util.UUID;

public record GameId(UUID uuid) {
    public GameId {
        Objects.requireNonNull(uuid);
    }
}
