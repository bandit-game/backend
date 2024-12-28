package be.kdg.integration5.common.events;

import java.util.List;
import java.util.Objects;

public record GameAddedEvent(
        String gameName,
        String description,
        Double price,
        String currency,
        int maxLobbyPlayersAmount,
        String frontendUrl,
        String backendApiUrl,
        String gameImageUrl,
        List<GameRule> rules) {


    public GameAddedEvent {
        Objects.requireNonNull(gameName);
        Objects.requireNonNull(description);
        Objects.requireNonNull(price);
        Objects.requireNonNull(currency);
        Objects.requireNonNull(frontendUrl);
        Objects.requireNonNull(backendApiUrl);
        Objects.requireNonNull(gameImageUrl);
        Objects.requireNonNull(rules);
    }

    public record GameRule(String rule, String description) {
        public GameRule {
            Objects.requireNonNull(rule);
            Objects.requireNonNull(description);
        }
    }
}
