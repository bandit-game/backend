package be.kdg.integration5.statisticscontext.domain;

import java.util.Objects;
import java.util.UUID;

public record MoveId(UUID uuid) {
    public MoveId {
        Objects.requireNonNull(uuid);
    }
}
