package be.kdg.integration5.gameplatformcontext.domain;

import java.util.Objects;
import java.util.UUID;

public record AchievementId(UUID uuid) {
    public AchievementId {
        Objects.requireNonNull(uuid);
    }
}
