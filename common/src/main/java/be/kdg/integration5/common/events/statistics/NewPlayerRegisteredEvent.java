package be.kdg.integration5.common.events.statistics;

import java.util.Objects;
import java.util.UUID;

public record NewPlayerRegisteredEvent(UUID playerId, String playerName, String gender, int age, String country, String city) {
    public NewPlayerRegisteredEvent {
        Objects.requireNonNull(playerName);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(country);
        Objects.requireNonNull(city);
    }
}
