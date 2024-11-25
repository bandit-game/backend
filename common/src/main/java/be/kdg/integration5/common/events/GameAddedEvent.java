package be.kdg.integration5.common.events;

import java.util.List;

public record GameAddedEvent(String gameName, String frontendUrl, String backendApiUrl, String gameImageUrl, List<GameRule> rules) {
    public record GameRule(String rule, String description) {

    }
}
