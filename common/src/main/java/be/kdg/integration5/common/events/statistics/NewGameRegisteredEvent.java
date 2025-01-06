package be.kdg.integration5.common.events.statistics;

import java.util.Objects;
import java.util.UUID;

public record NewGameRegisteredEvent(UUID gameId, String gameName)  {
    public NewGameRegisteredEvent {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(gameName);
    }
}
